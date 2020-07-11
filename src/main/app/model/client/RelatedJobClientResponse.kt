package app.model.client

import kotlinx.serialization.Serializable

@Serializable
data class RelatedJobClientResponse (
        val related_job_titles: List<RelatedJobClientResponseEntry>,
        val unusual_job_titles: List<RelatedJobClientResponseEntry>
)