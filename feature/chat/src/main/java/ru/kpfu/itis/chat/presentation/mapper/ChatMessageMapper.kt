package ru.kpfu.itis.chat.presentation.mapper

import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.presentation.screen.chat.ChatMessage
import ru.kpfu.itis.core_ui.extension.convertLongToTime

fun ChatMessage.mapToModel(): ChatMessageModel {
    return ChatMessageModel(
        message = this.message,
        sender = ChatFriendModel(
            id = this.sender?.id,
            name = this.sender?.name,
            profileImage = this.sender?.profileImage
        ),
        time = System.currentTimeMillis()
    )
}

fun List<ChatMessage>.mapToModel(): List<ChatMessageModel> {
    return this.map(ChatMessage::mapToModel)
}

fun ChatMessageModel.mapFromModel(isMyMessage: Boolean): ChatMessage {
    return ChatMessage(
        message = this.message,
        sender = ChatFriendModel(
            id = this.sender?.id,
            name = this.sender?.name,
            profileImage = this.sender?.profileImage
        ),
        time = this.time?.convertLongToTime(),
        isMyMessage = isMyMessage
    )
}