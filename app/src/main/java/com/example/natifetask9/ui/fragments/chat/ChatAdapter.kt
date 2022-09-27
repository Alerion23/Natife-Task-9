package com.example.natifetask9.ui.fragments.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.natifetask9.databinding.EmptyViewHolderBinding
import com.example.natifetask9.databinding.MyMessageBinding
import com.example.natifetask9.databinding.OtherMessageBinding
import com.example.natifetask9.model.Message

class ChatAdapter(
    private val otherUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Message> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                val myMessageBinding =
                    MyMessageBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                SentMessageHolder(myMessageBinding)
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                val otherMessageBinding =
                    OtherMessageBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                ReceivedMessageHolder(otherMessageBinding)
            }
            else -> {
                val emptyBinding =
                    EmptyViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                EmptyViewHolder(emptyBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        when (message.user) {
            Message.Sender.ME -> {
                if (message.otherUserId == otherUserId) {
                    (holder as SentMessageHolder).onBind(message)
                }
            }
            Message.Sender.OTHER_USER -> {
                if (message.otherUserId == otherUserId) {
                    (holder as ReceivedMessageHolder).onBind(message)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return when (message.user) {
            Message.Sender.ME -> {
                if (message.otherUserId == otherUserId) {
                    VIEW_TYPE_MESSAGE_SENT
                } else {
                    VIEW_TYPE_INCORRECT_ID
                }
            }
            Message.Sender.OTHER_USER -> {
                if (message.otherUserId == otherUserId) {
                    VIEW_TYPE_MESSAGE_RECEIVED
                } else {
                    VIEW_TYPE_INCORRECT_ID
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun setMessageList(messageList: List<Message>) {
        list = messageList
        notifyDataSetChanged()
    }

    class SentMessageHolder(
        private val binding: MyMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentMessage: Message) {
            binding.textMessageMe.text = currentMessage.message
        }
    }

    class ReceivedMessageHolder(
        private val binding: OtherMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(currentMessage: Message) {
            binding.textMessageOther.text = currentMessage.message
        }
    }

    class EmptyViewHolder(
        private val binding: EmptyViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
        private const val VIEW_TYPE_INCORRECT_ID = 3
    }
}