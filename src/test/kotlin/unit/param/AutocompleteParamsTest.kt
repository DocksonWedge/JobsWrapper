package unit.param

import app.model.param.AutocompleteParams
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.element
import io.kotest.property.exhaustive.collection
import io.kotest.property.forAll
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.asserter


class AutocompleteParamsTest() : StringSpec({

    // ----------- property based test ------------
    //TODO make it so we expect exceptions for autocomplete params
    "Autocomplete parameters always use the first non-empty value"{
        forAll(30,
                Exhaustive.collection(listOf(
                        "",
                        " ",
                        "~`!@#$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./")),
                Arb.element(listOf(
                        "",
                        "\n",
                        "assistant")),
                Arb.element(listOf(
                        "",
                        "   ",
                        "the quick brown fox jumps over the lazy dog")))
        { begins_with, contains, ends_with ->

            var error = false
            var result: Pair<String, String>
            try {
                 result = AutocompleteParams(begins_with, contains, ends_with).getEffectiveParam()
            }catch(e: IllegalArgumentException){
                error = true
                result = "" to ""
            }

            // return first non-null
            //TODO hmmm.... did we almost just re-write the prod function...
            when {
                begins_with.isNotBlank() -> {
                    assertParams(result.first, "begins_with", result.second, begins_with)
                }
                contains.isNotBlank() -> {
                    assertParams(result.first, "contains", result.second, contains)
                }
                ends_with.isNotBlank() -> {
                    assertParams(result.first, "ends_with", result.second, ends_with)
                }
                else -> {
                    asserter.assertTrue("Should have an error when all blank params", error)
                }
            }
            true
        }
    }

    // ----------- static style test ------------
    "Autocomplete given all empty strings"{
        assertThrows<IllegalArgumentException> {
            AutocompleteParams("", "", " ").getEffectiveParam()
        }
    }
    "Autocomplete given all empty strings except ends_with"{
        asserter.assertEquals("should return ends_with",
                "ends_with" to "c", AutocompleteParams("", "", "c").getEffectiveParam())
    }
    "Autocomplete given all non-empty strings for params"{
        asserter.assertEquals("should return begins_with",
                "begins_with" to "a", AutocompleteParams("a", "b", "c").getEffectiveParam())
    }
    "Autocomplete given only one non-empty string"{
        asserter.assertEquals("should return contains",
                "contains" to "b", AutocompleteParams("", "b", "").getEffectiveParam())
    }
})

fun assertParams(actualName: String, expectedName: String, actualValue: String, expectedValue: String) {
    asserter.assertEquals("Parameter selected by AutocompleteParams was wrong.",
            expectedName, actualName)
    asserter.assertEquals("Parameter VALUE returned from AutocompleteParams was wrong.",
            expectedValue, actualValue)
}