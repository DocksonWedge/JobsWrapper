package app.model

import app.core.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SkillsAutocompleteEntry(
        val uuid: String,
        val normalized_skill_name: String,
        val suggestion: String
)
