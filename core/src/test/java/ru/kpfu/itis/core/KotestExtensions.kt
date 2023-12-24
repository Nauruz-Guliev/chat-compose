package ru.kpfu.itis.core

import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecWhenContainerScope
import io.kotest.core.test.TestScope

public suspend fun BehaviorSpecGivenContainerScope.When(
    test: suspend BehaviorSpecWhenContainerScope.() -> Unit
) {
    When("call test class", test)
}


public suspend fun BehaviorSpecWhenContainerScope.Then(test: suspend TestScope.() -> Unit) {
    Then("should verify result", test)
}