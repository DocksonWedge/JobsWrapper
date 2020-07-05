package app.model

import app.core.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

// Unused. Was mislead by documentation. Might use later.
@Serializable
data class JobEntry(
        val importance: Integer,
        val job_title: String,
        @Serializable(UUIDSerializer::class)
        val job_uuid: UUID,
        val level: Integer,
        val normalized_job_title: String
)