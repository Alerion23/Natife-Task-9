package com.example.natifetask9.utils

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.MessageDto

fun MessageDto.toMessage(sender: Message.Sender) = Message(
    otherUserId = from.id,
    user = sender,
    message = message,
)