package ru.kpfu.itis.chat.presentation.screen.chat_list

data class ChatListViewState(
    val chatList: List<ChatItem> = emptyList(),
    val screenState: ChatListScreenState = ChatListScreenState.LOADING
)

enum class ChatListScreenState {
    CHATS_LOADED,
    NO_CHATS,
    LOADING
}
