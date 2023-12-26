package ru.kpfu.itis.core_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost

abstract class BaseViewModel<STATE : Any, SIDE_EFFECT : Any> :
    ContainerHost<STATE, SIDE_EFFECT>,
    ViewModel() {
    abstract override val container: Container<STATE, SIDE_EFFECT>

    fun runOnMainThread(block: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            block.invoke()
        }
    }

    fun NavHostController.navigateLosingBackStack(destination: String) {
        viewModelScope.launch(Dispatchers.Main) {
            this@navigateLosingBackStack.navigate(destination) {
                popUpTo(0)
            }
        }
    }

    fun NavHostController.navigateSavingBackStack(destination: String) {
        viewModelScope.launch(Dispatchers.Main) {
            this@navigateSavingBackStack.navigate(destination)
        }
    }
}