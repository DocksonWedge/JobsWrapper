package app.model.param

import kotlinx.serialization.Serializable
import java.lang.IllegalArgumentException

@Serializable
class AutocompleteParams(
        private val begins_with: String,
        private val contains: String,
        private val ends_with: String) {

    fun getEffectiveParam(): Pair<String, String> {
        try {
            return listOf(
                    "begins_with" to begins_with, "contains" to contains, "ends_with" to ends_with
            ).first { it.second.isNotBlank() }
        }catch (e: NoSuchElementException){
            throw IllegalArgumentException("At least one non-blank parameter is required.")
        }
    }
}