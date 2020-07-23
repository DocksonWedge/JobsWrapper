package unit.param

import app.model.param.AutocompleteParams
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Exhaustive
import io.kotest.property.exhaustive.collection
import io.kotest.property.forAll
import kotlin.test.asserter


class AutocompleteParamsTest() : StringSpec({

    // ----------- property based test ------------
    //TODO make it so we expect exceptions for autocomplete params
    "Autocomplete parameters always use the first non-empty value"{
        forAll(
                Exhaustive.collection(listOf(
                        "",
                        " ",
                        "~`!@#$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./")),
                Exhaustive.collection(listOf(
                        "",
                        "\n",
                        "assistant")),
                Exhaustive.collection(listOf(
                        "",
                        "   ",
                        "the quick brown fox jumps over the lazy dog")))
        { begins_with, contains, ends_with ->
            val result = AutocompleteParams(begins_with, contains, ends_with).getEffectiveParam()
            // return first non-null
            //TODO hmmm.... did we almost just re-write the prod function...
            when {
                begins_with != "" -> {
                    assertParams(result.first, "begins_with", result.second, begins_with)
                }
                contains != "" -> {
                    assertParams(result.first, "contains", result.second, contains)
                }
                ends_with != "" -> {
                    assertParams(result.first, "ends_with", result.second, ends_with)
                }
                else -> {
                    assertParams(result.first, "ends_with", result.second, ends_with)
                }
            }
            true
        }
    }

    // ----------- static style test ------------

    "Autocomplete given all empty strings for params"{
        asserter.assertEquals("should return ends_with",
                "ends_with" to "", AutocompleteParams("", "", "").getEffectiveParam())
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
            actualName, expectedName)
    asserter.assertEquals("Parameter VALUE returned from AutocompleteParams was wrong.",
            actualValue, expectedValue)
}