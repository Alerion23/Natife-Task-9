package com.example.natifetask9.utils

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.MessageDto

fun MessageDto.toMessage(sender: Message.Sender, id: String) = Message(
    otherUserId = id,
    user = sender,
    message = message,
)