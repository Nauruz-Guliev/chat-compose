package ru.kpfu.itis.coretesting

import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecWhenContainerScope
import io.kotest.core.test.TestScope

suspend fun BehaviorSpecGivenContainerScope.When(
    test: suspend BehaviorSpecWhenContainerScope.() -> Unit
) {
    When("call test class", test)
}


suspend fun BehaviorSpecWhenContainerScope.Then(test: suspend TestScope.() -> Unit) {
    Then("should verify result", test)
}