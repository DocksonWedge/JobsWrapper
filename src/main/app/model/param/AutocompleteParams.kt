package app.model.param

import kotlinx.serialization.Serializable

@Serializable
class AutocompleteParams(
        private val begins_with: String,
        private val contains: String,
        private val ends_with: String) {

    fun getEffectiveParam(): Pair<String, String> {
        return listOf(
                "begins_with" to begins_with, "contains" to contains, "ends_with" to ends_with
        ).first { it.second != "" || it.first == "ends_with"}
    }
}