package app.model.response

import kotlinx.serialization.Serializable

@Serializable
data class JobsAutocompleteEntry (
        val parent_uuid: String,
        val normalized_job_title: String,
        override val uuid: String,
        override val suggestion: String
) : AutocompleteEntry

