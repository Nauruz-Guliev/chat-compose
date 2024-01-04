package ru.kpfu.itis.chat.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.kpfu.itis.chat.domain.model.ChatListModel
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.model.ChatUserModel

const val USER_ID = "id"
const val CHAT_ID = "chatId"

fun getChatListFlow(): Flow<List<ChatListModel>> = flowOf(
    listOf(
        ChatListModel(
            user = getUser(),
            chatId = CHAT_ID,
            lastUpdated = 123142154
        )
    )
)

fun getUser(): ChatUserModel {
    return ChatUserModel(
        id = USER_ID,
        name = "user",
        profileImage = "cool-image.com"
    )
}

fun getMessagesFlow(): Flow<List<ChatMessageModel>> {
    return flowOf(
        listOf(
            getMessageModel()
        )
    )
}

fun getMessageModel(): ChatMessageModel {
    return ChatMessageModel(
        sender = getUser(),
        message = "HAPPY NEW YEAR",
        time = 123142154
    )
}
