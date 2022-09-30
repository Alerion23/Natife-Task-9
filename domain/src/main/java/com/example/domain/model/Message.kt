package com.example.domain.model

data class Message(
    val otherUserId: String,
    val user: Sender,
    val message: String
) {

    enum class Sender {
        ME, OTHER_USER
    }
}
