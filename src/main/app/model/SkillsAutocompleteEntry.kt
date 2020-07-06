package app.model

import kotlinx.serialization.Serializable

@Serializable
data class SkillsAutocompleteEntry(
        override val uuid: String,
        val normalized_skill_name: String,
        override val suggestion: String
) : AutocompleteEntry
