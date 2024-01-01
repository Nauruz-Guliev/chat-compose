package ru.kpfu.itis.core_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import ru.kpfu.itis.core_ui.validation.ValidationResult
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<STATE : Any, SIDE_EFFECT : Any> :
    ContainerHost<STATE, SIDE_EFFECT>,
    ViewModel() {

    abstract override val container: Container<STATE, SIDE_EFFECT>

    fun SimpleSyntax<STATE, SIDE_EFFECT>.runReadWriteTask(block: suspend () -> Unit) {
        runCoroutine(Dispatchers.IO, block)
    }

    fun SimpleSyntax<STATE, SIDE_EFFECT>.runOnMainThread(block: suspend () -> Unit) {
        runCoroutine(Dispatchers.Main, block)
    }

    fun SimpleSyntax<STATE, SIDE_EFFECT>.runComputationTask(block: suspend () -> Unit) {
        runCoroutine(Dispatchers.Default, block)
    }

    fun SimpleSyntax<STATE, SIDE_EFFECT>.isValidationSuccessful(state: STATE): Boolean {
        return !state::class.java.declaredFields.any { field ->
            field.run {
                isAccessible = true
                get(state)?.let {
                    ValidationResult.Failure::class == field.get(state)::class
                } ?: false
            }
        }
    }

    private fun runCoroutine(dispatcher: CoroutineContext, block: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) {
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
