package ru.kpfu.itis.chat.presentation.screen.chat_list

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.chat.domain.repository.UserRepository
import ru.kpfu.itis.core_ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val navController: NavHostController,
    private val repository: UserRepository
) : BaseViewModel<ChatListViewState, ChatListViewSideEffect>() {

    override val container: Container<ChatListViewState, ChatListViewSideEffect> =
        container(ChatListViewState())

    fun load() {
        viewModelScope.launch {
            repository.getAllUsers()
        }
    }
}