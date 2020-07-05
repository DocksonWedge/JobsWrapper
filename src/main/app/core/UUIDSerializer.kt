package app.core

import kotlinx.serialization.*
import java.util.*

// TODO: unit test
// TODO: re-write to re-format un-dashed uuids
@Serializer(forClass = UUID::class)
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(output: Encoder, obj: UUID) {
        output.encodeString(obj.toString())
    }

    @ImplicitReflectionSerializer
    override fun deserialize(input: Decoder): UUID {
        return UUID.fromString(input.decode())
    }
}

