package ru.kpfu.itis.chat.data.remote.mapper

import ru.kpfu.itis.chat.data.remote.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatMessageModel

fun ChatMessageModel.mapToDto(): ChatMessageDto {
    return ChatMessageDto(
        senderId = this.sender?.id,
        message = this.message,
        time = this.time
    )
}