package ru.kpfu.itis.chat.data.local.mapper

import ru.kpfu.itis.chat.data.local.entity.ChatMessageEntity
import ru.kpfu.itis.chat.data.local.entity.ChatUserEntity
import ru.kpfu.itis.chat.data.remote.model.ChatMessageDto
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.model.ChatUserModel

fun ChatMessageDto.mapToEntity(sender: ChatUserEntity?, chatId: String?): ChatMessageEntity {
    return ChatMessageEntity(
        time = time,
        sender = sender,
        text = message,
        chatId = chatId
    )
}

fun ChatMessageEntity.mapToModel(sender: ChatUserModel?): ChatMessageModel {
    return ChatMessageModel(
        sender = sender,
        message = this.text,
        time = time
    )
}