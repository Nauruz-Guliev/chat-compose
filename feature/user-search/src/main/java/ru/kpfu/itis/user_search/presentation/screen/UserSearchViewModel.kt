package ru.kpfu.itis.user_search.presentation.screen

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.chat_api.ChatDestinations
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.user_search.domain.usecase.CreateChat
import ru.kpfu.itis.user_search.domain.usecase.FindUser
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val findUser: FindUser,
    private val createChat: CreateChat,
    private val navController: NavHostController
) : BaseViewModel<UserSearchState, UserSearchSideEffect>() {

    override val container: Container<UserSearchState, UserSearchSideEffect> =
        container(UserSearchState())

    fun findUser(name: String) = intent {
        if (name.isBlank()) return@intent
        findUser.invoke(name)
            .stateIn(viewModelScope)
            .catch {
                postSideEffect(UserSearchSideEffect.ExceptionHappened(it))
            }
            .map { users ->
                users.map { chatUser ->
                    ChatUserSearchModel(
                        chatUser,
                        isProfileImageValid(chatUser.profileImage)
                    )
                }
            }
            .collect { listOfChatUsers ->
                reduce { state.copy(users = listOfChatUsers) }
            }
    }

    fun startChatting(friendId: String) = intent {
        runReadWriteTask {
            runCatching {
                createChat.invoke(friendId)
            }.onFailure { exception ->
                postSideEffect(UserSearchSideEffect.ExceptionHappened(exception))
            }.onSuccess {
                navController.navigateSavingBackStack(ChatDestinations.CHAT_SCREEN.name)
            }
        }
    }

    private fun isProfileImageValid(profileImageUrl: String?): Boolean {
        return profileImageUrl?.let { Patterns.WEB_URL.matcher(it).matches() } ?: false
    }
}