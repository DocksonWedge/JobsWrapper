package unit.param

import app.model.param.AutocompleteParams
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class AutocompleteParamsTest() {

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
            Assertions.assertEquals(expectedVal, params.getEffectiveParam())
        }
    }
}