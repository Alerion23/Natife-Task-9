package com.example.data.utils

import com.example.domain.model.Message
import com.example.domain.model.MessageDto

fun MessageDto.toMessage(sender: Message.Sender) = Message(
    otherUserId = from.id,
    user = sender,
    message = message,
)