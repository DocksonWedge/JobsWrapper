package app.model.response

import app.core.ResultTypeSerializer
import app.enum.ResultType
import kotlinx.serialization.Serializable

@Serializable
data class FirstResult(
        var normalized_title: String,
        val uuid: String,
        val suggestion: String,
        @Serializable(ResultTypeSerializer::class)
        var type : ResultType
)