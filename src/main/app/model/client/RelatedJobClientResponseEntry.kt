package app.model.client

import kotlinx.serialization.Serializable

@Serializable
class RelatedJobClientResponseEntry(
        val title: String,
        val uuid: String,
        val parent_uuid: String
)