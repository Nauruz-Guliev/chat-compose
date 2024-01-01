package ru.kpfu.itis.chat.presentation.screen.chat

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.chat.domain.model.ChatFriendModel
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.usecase.GetCurrentUserId
import ru.kpfu.itis.chat.domain.usecase.GetMessages
import ru.kpfu.itis.chat.domain.usecase.SendMessage
import ru.kpfu.itis.chat.presentation.mapper.mapFromModel
import ru.kpfu.itis.core_ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    private val getMessages: GetMessages,
    private val getCurrentUserId: GetCurrentUserId,
    private val navController: NavHostController
) : BaseViewModel<ChatViewState, ChatSideEffect>() {

    override val container: Container<ChatViewState, ChatSideEffect> =
        container(ChatViewState())

    init {
        intent {
            runCatching {
                getCurrentUserId()
            }.onSuccess { id ->
                reduce { state.copy(currentUserId = id) }
            }
        }
    }

    fun loadMessages(chatId: String) = intent {
        delay(200)
        setChatId(chatId)
        getMessages(chatId).stateIn(viewModelScope)
            .map { listOfMessages ->
                listOfMessages.map { chatModel ->
                    chatModel.mapFromModel(isMyMessage = chatModel.sender?.id == state.currentUserId)
                }
            }
            .flowOn(Dispatchers.IO)
            .catch { exception ->
                postSideEffect(ChatSideEffect.ExceptionHappened(exception))
            }
            .collect { listOfMappedMessages ->
                reduce {
                    state.copy(
                        messages = listOfMappedMessages,
                        isLoading = false
                    )
                }
            }
    }

    private fun setChatId(chatId: String) = intent {
        if (state.chatId.isBlank()) {
            reduce { state.copy(chatId = chatId) }
        }
    }

    fun onSendClicked(message: String) = intent {
        if (message.isBlank()) return@intent
        runCatching {
            sendMessage(
                state.chatId, ChatMessageModel(
                    sender = ChatFriendModel(id = state.currentUserId),
                    message = message,
                    time = System.currentTimeMillis()
                )
            )
        }.onFailure { ex ->
            postSideEffect(ChatSideEffect.ExceptionHappened(ex))
        }
    }
}