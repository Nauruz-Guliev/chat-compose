package ru.kpfu.itis.chat.presentation.screen.chat_list

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.chat.domain.usecase.GetChatList
import ru.kpfu.itis.chat.presentation.mapper.mapToItem
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core_ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatList: GetChatList,
    private val navController: NavHostController,
) : BaseViewModel<ChatListViewState, ChatListSideEffect>() {

    override val container: Container<ChatListViewState, ChatListSideEffect> =
        container(ChatListViewState())

    init {
        intent {
            loadChats()
        }
    }

    private fun loadChats() = intent {
        getChatList()
            .stateIn(viewModelScope)
            .catch { exception ->
                postSideEffect(ChatListSideEffect.ExceptionHappened(exception))
            }
            .collect { chatList ->
                reduce {
                    state.copy(
                        chatList = chatList.mapToItem(),
                        screenState = if (chatList.isNotEmpty()) {
                            ChatListScreenState.CHATS_LOADED
                        } else {
                            ChatListScreenState.NO_CHATS
                        }
                    )
                }
            }
    }

    fun onChatClicked(chatId: String?) = intent {
        navController.navigateSavingBackStack(ChatDestinations.CHAT_SCREEN.name, chatId)
    }
}