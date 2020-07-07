package app.model

import kotlinx.serialization.Serializable

@Serializable
data class FirstResult(var normalized_title: String, val uuid: String, val suggestion: String)