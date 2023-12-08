package ru.kpfu.itis.authentication

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ExampleUnitTest : BehaviorSpec({

    Given("Values") {
        val a = 2
        val b = 2
        When("Adding") {
            val result = a + b
            Then("Result should be 2") {
                result shouldBe 4
            }
        }
    }
})
