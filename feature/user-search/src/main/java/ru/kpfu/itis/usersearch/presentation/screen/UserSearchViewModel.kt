package ru.kpfu.itis.usersearch.presentation.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.coreui.base.BaseViewModel
import ru.kpfu.itis.usersearch.domain.usecase.CreateChat
import ru.kpfu.itis.usersearch.domain.usecase.FindUser
import ru.kpfu.itis.usersearch.domain.usecase.LoadExistingChats
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val findUser: FindUser,
    private val createChat: CreateChat,
    private val loadExistingChats: LoadExistingChats
) : BaseViewModel<UserSearchState, UserSearchSideEffect>() {

    override val container: Container<UserSearchState, UserSearchSideEffect> =
        container(UserSearchState())

    fun findUser(name: String) = intent {
        if (name.isBlank()) return@intent
        combine(
            findUser.invoke(name),
            loadExistingChats()
        ) { listOfChatUsers, listOfExistingChats ->
            val users = listOfChatUsers.map { chatUser ->
                val isExistingChat = listOfExistingChats.contains(chatUser.id)
                ChatUserSearchModel(
                    chatUser,
                    isProfileImageValid(chatUser.profileImage),
                    isExistingChat
                )
            }
            users
        }
            .stateIn(viewModelScope)
            .catch {
                postSideEffect(UserSearchSideEffect.ExceptionHappened(it))
            }
            .collect { listOfChatUsers ->
                reduce { state.copy(users = listOfChatUsers) }
            }
    }

    fun createChat(friendId: String) = intent {
        runReadWriteTask {
            runCatching {
                createChat.invoke(friendId)
            }.onFailure { exception ->
                postSideEffect(UserSearchSideEffect.ExceptionHappened(exception))
            }
        }
    }

    private fun isProfileImageValid(profileImageUrl: String?): Boolean {
        return profileImageUrl?.let { Patterns.WEB_URL.matcher(it).matches() } ?: false
    }

    fun resetState() = intent {
        reduce {
            state.copy(users = emptyList(), existingChats = emptyList())
        }
    }
}
