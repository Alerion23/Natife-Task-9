package com.example.natifetask9.server

import com.example.natifetask9.model.*
import com.example.natifetask9.utils.toMessage
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket

class TCPClientImpl : TCPClient {

    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private var pingPongJob: Job? = null
    private var userId: String? = null
    private var chatMessages = MutableStateFlow<List<Message>>(emptyList())
    private var usersList = MutableStateFlow<List<User>>(emptyList())
    private val parentJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + parentJob)
    private val isConnected = MutableStateFlow(false)
    private val isAuthComplete = MutableStateFlow(false)
    private val gson = Gson()

    @DelicateCoroutinesApi
    override fun startConnection(ip: InetAddress, port: Int, userName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var response: String?
            socket = Socket(ip, port)
            writer = PrintWriter(OutputStreamWriter(socket?.getOutputStream()))
            reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
            isConnected.emit(true)
            while (isConnected.value) {
                try {
                    response = reader?.readLine()
                    if (response != null) {
                        val responseModel = gson.fromJson(response, BaseDto::class.java)
                        when (responseModel.action) {
                            BaseDto.Action.CONNECTED -> {
                                onConnectedResponse(responseModel, userName)
                            }
                            BaseDto.Action.USERS_RECEIVED -> {
                                onUsersReceivedResponse(responseModel)
                            }
                            BaseDto.Action.NEW_MESSAGE -> {
                                onNewMessageResponse(responseModel)
                            }
                            BaseDto.Action.PONG -> {
                                onPongResponse()
                            }
                            else -> {}
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun onNewMessageResponse(responseModel: BaseDto) {
        val messageModel = gson.fromJson(responseModel.payload, MessageDto::class.java)
        val otherUserId = messageModel.from.id
        val message = messageModel.toMessage(Message.Sender.OTHER_USER, otherUserId)
        val currentList = chatMessages.value
        chatMessages.value = currentList + message
    }

    override fun getAuthStatus(): Flow<Boolean> {
        return isAuthComplete
    }

    private fun onUsersReceivedResponse(responseModel: BaseDto) {
        val usersModel = gson.fromJson(responseModel.payload, UsersReceivedDto::class.java)
        usersList.value = usersModel.users
    }

    private fun onPongResponse() {
        pingPongJob?.cancel()
    }

    private fun onConnectedResponse(responseModel: BaseDto, userName: String) {
        val connectedModel =
            gson.fromJson(
                responseModel.payload,
                ConnectedDto::class.java
            )
        userId = connectedModel.id
        userId?.let {
            val connectionString = gson.toJson(ConnectDto(it, userName))
            val connectionJsonString =
                gson.toJson(
                    BaseDto(
                        BaseDto.Action.CONNECT,
                        connectionString
                    )
                )
            send(connectionJsonString)
            isAuthComplete.value = true
            startPingingServer(it)
        }
    }

    private fun startPingingServer(id: String) {
        scope.launch {
            while (isConnected.value) {
                val pingString = gson.toJson(PingDto(id))
                val pingJsonString =
                    gson.toJson(BaseDto(BaseDto.Action.PING, pingString))
                send(pingJsonString)
                delay(8000)
                pingPongJob = scope.launch {
                    delay(10000)
                    disconnect()
                }
            }
        }
    }

    private fun send(message: String) {
        writer?.println(message)
        writer?.flush()
    }

    override fun sendMessageForChat(text: String, receiverId: String) {
        userId?.let {
            val message = Message(receiverId, Message.Sender.ME, text)
            val currentList = chatMessages.value
            chatMessages.value = currentList + message
            val messageString = gson.toJson(SendMessageDto(it, receiverId, text))
            val messageJsonString = gson.toJson(BaseDto(BaseDto.Action.SEND_MESSAGE, messageString))
            send(messageJsonString)
        }
    }

    override fun getMessageForChat(): Flow<List<Message>> {
        return chatMessages
    }

    override fun sendGetUsers() {
        userId?.let {
            val userRequestString = gson.toJson(GetUsersDto(it))
            val userRequestJsonString =
                gson.toJson(BaseDto(BaseDto.Action.GET_USERS, userRequestString))
            send(userRequestJsonString)
        }
    }

    override fun getUsers(): Flow<List<User>> {
        return usersList
    }

    override fun disconnect() {
        socket?.close()
        writer?.close()
        reader?.close()
        isAuthComplete.value = false
        isConnected.value = false
        parentJob.cancelChildren()
    }
}