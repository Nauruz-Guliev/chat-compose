package ru.kpfu.itis.navigation

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: BehaviorSpec( {

    Given("Numbers") {
        When("Multiplying") {
            Then("Result should be as expected"){
                4 * 4 shouldBe 16
            }
        }
    }
})