package ru.kpfu.itis.chat.presentation.mapper

import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.model.ChatUserModel
import ru.kpfu.itis.chat.presentation.screen.chat.ChatMessage
import ru.kpfu.itis.coreui.extension.convertLongToTime

fun ChatMessageModel.mapFromModel(isMyMessage: Boolean): ChatMessage {
    return ChatMessage(
        message = this.message,
        sender = ChatUserModel(
            id = this.sender?.id,
            name = this.sender?.name,
            profileImage = this.sender?.profileImage
        ),
        time = this.time.convertLongToTime(),
        isMyMessage = isMyMessage
    )
}
