package app.model

import kotlinx.serialization.Serializable

@Serializable
data class JobsAutocompleteEntry(
        val parent_uuid: String,
        val normalized_job_title: String,
        val uuid: String,
        val suggestion: String
)
