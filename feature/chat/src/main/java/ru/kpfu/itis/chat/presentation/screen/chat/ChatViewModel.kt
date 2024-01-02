package ru.kpfu.itis.chat.presentation.screen.chat

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.chat.domain.model.ChatMessageModel
import ru.kpfu.itis.chat.domain.model.ChatUserModel
import ru.kpfu.itis.chat.domain.usecase.GetCurrentUserId
import ru.kpfu.itis.chat.domain.usecase.GetMessages
import ru.kpfu.itis.chat.domain.usecase.GetSenderProfile
import ru.kpfu.itis.chat.domain.usecase.SendMessage
import ru.kpfu.itis.chat.presentation.mapper.mapFromModel
import ru.kpfu.itis.core_ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    private val getMessages: GetMessages,
    private val getCurrentUserId: GetCurrentUserId,
    private val getSenderProfile: GetSenderProfile,
) : BaseViewModel<ChatState, ChatSideEffect>() {

    override val container: Container<ChatState, ChatSideEffect> =
        container(ChatState())

    init {
        setCurrentUserId()
    }

    private fun setCurrentUserId() = intent {
        runCatching {
            getCurrentUserId()
        }.onSuccess { id ->
            reduce { state.copy(currentUserId = id) }
        }
    }

    fun loadMessages(chatId: String) = intent {
        setSenderProfile(chatId)
        setChatId(chatId)
        getMessages(chatId).stateIn(viewModelScope)
            .map { listOfMessages ->
                listOfMessages.map { chatModel ->
                    chatModel.mapFromModel(isMyMessage = chatModel.sender?.id == state.currentUserId)
                }
            }
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

    private fun setSenderProfile(chatId: String) = intent {
        if (state.sender != null) return@intent
        runCatching {
            getSenderProfile(chatId)
        }.onSuccess { user ->
            reduce { state.copy(sender = user) }
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
                    sender = ChatUserModel(id = state.currentUserId),
                    message = message,
                    time = System.currentTimeMillis()
                )
            )
        }.onFailure { ex ->
            postSideEffect(ChatSideEffect.ExceptionHappened(ex))
        }
    }
}