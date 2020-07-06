package app.model

import kotlinx.serialization.Serializable

@Serializable
data class SkillsAutocompleteEntry(
        val uuid: String,
        val normalized_skill_name: String,
        val suggestion: String
)
