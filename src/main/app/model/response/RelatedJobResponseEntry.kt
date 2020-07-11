package app.model.response

import app.core.ResultTypeSerializer
import app.enum.ResultType
import kotlinx.serialization.Serializable


@Serializable
data class RelatedJobResponseEntry (
        val uuid: String,
        val title: String,
        @Serializable(ResultTypeSerializer::class)
        val type: ResultType
)