package ru.kpfu.itis.chat.data.remote.mapper

import ru.kpfu.itis.chat.data.remote.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.model.ChatMessageModel

fun ChatMessageModel.mapToDto(): ChatMessageDto {
    return ChatMessageDto(
        senderId = this.sender?.id,
        message = this.message,
        time = this.time ?: System.currentTimeMillis()
    )
}

fun ChatMessageDto.mapToModel(sender: ChatFriendModel?): ChatMessageModel {
    return ChatMessageModel(
        sender = sender,
        message = this.message,
        time = this.time
    )
}