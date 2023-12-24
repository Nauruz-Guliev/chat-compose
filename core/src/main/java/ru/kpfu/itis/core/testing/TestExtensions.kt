package ru.kpfu.itis.core.testing

import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecWhenContainerScope
import io.kotest.core.test.TestScope

/**
 * Данные расширения нужны для того, чтобы в When и Then блоках не приходилось писать текст в тестах.
 */

suspend fun BehaviorSpecGivenContainerScope.When(
    test: suspend BehaviorSpecWhenContainerScope.() -> Unit
) {
    When("call test class", test)
}


suspend fun BehaviorSpecWhenContainerScope.Then(test: suspend TestScope.() -> Unit) {
    Then("should verify result", test)
}