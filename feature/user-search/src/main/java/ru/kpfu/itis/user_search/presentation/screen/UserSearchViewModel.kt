package ru.kpfu.itis.user_search.presentation.screen

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.core_ui.base.BaseViewModel
import ru.kpfu.itis.user_search.domain.usecase.FindUser
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val findUser: FindUser
) : BaseViewModel<UserSearchState, UserSearchSideEffect>() {

    override val container: Container<UserSearchState, UserSearchSideEffect> =
        container(UserSearchState())

    fun findUser(name: String) = intent {
        if (name.isBlank()) return@intent
        runCatching {
            findUser.invoke(name)
                .stateIn(viewModelScope)
                .collect { users ->
                    reduce { state.copy(users = users) }
                }
        }
    }
}