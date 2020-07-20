package unit.param

import app.model.param.AutocompleteParams
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Exhaustive
import io.kotest.property.exhaustive.collection
import io.kotest.property.exhaustive.ints
import io.kotest.property.forAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.random.Random
import kotlin.test.asserter


class AutocompleteParamsTest() : StringSpec({


    "String size" {
        forAll<String, String>(5) { a, b ->
            println("attempt: a:$a b:$b")
            (a + b).length == a.length + b.length
        }
    }

    "Effective parameter selection"{
        forAll(
                Exhaustive.collection(listOf(
                        "",
                        " ",
                        "~`!@#$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./")),
                Exhaustive.collection(listOf(
                        "",
                        " ",
                        "assistant")),
                Exhaustive.collection(listOf(
                        "",
                        " ",
                        "the quick brown fox jumps over the lazy dog")))
        { begins_with, contains, ends_with ->
            val result = AutocompleteParams(begins_with, contains, ends_with).getEffectiveParam()
            // return first non-null
            //TODO hmmm.... we just re-wrote the prod function...
            when {
                begins_with != "" -> {
                    asserter.assertTrue(
                            "parameter name expected begins_with received ${result.first} , " +
                                    "parameter value expected $begins_with recieved ${result.second}",
                            result.first == "begins_with" && result.second == begins_with)
                }
                contains != "" -> {
                    result.first == "contains" && result.second == contains
                }
                ends_with != "" -> {
                    result.first == "ends_with" && result.second == ends_with
                }
                else -> {
                    result.first == "begins_with" && result.second == ""
                }
            }
            true
        }
    }
}) {


    //TODO remove?
    @TestFactory
    fun getEffectiveParamTest() = listOf(
            AutocompleteParams("assistant", "build", "cart")
                    to ("begins_with" to "assistant"),
            AutocompleteParams("", "build", "cart")
                    to ("contains" to "build"),
            AutocompleteParams("", "", "cart")
                    to ("ends_with" to "cart"),
            AutocompleteParams("", "", "")
                    to ("begins_with" to ""),
            AutocompleteParams(" ", "", "")
                    to ("begins_with" to " "),
            AutocompleteParams("assistant", "build", "")
                    to ("begins_with" to "assistant"),
            AutocompleteParams("", "the quick brown fox jumps over the lazy dog", "")
                    to ("contains" to "the quick brown fox jumps over the lazy dog"),
            AutocompleteParams("~`!@#$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./", "", "cart")
                    to ("begins_with" to "~`!@#\$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./")
    ).map { (params, expectedVal) ->
        DynamicTest.dynamicTest(
                "When I pass $params to JobsWrapper " +
                        "then I pass $expectedVal to the DAW api.")
        {
            println("start $params$expectedVal")
            Assertions.assertEquals(expectedVal, params.getEffectiveParam())
            Thread.sleep(Random.nextLong(1000))
            println("end $params$expectedVal")
        }
    }
}