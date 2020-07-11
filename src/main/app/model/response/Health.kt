package app.model.response

import kotlinx.serialization.*

@Serializable
data class Health(
        val status: String = "API is running a little at least."
)