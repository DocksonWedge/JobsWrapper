package app.core

import app.enum.ResultType
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializer(forClass = ResultType::class)
object ResultTypeSerializer {

    override fun deserialize(decoder: Decoder): ResultType {
        return ResultType.valueOf(decoder.decodeString().toUpperCase())
    }

    override fun serialize(encoder: Encoder, value: ResultType) {
        encoder.encodeString(value.name.toLowerCase())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveDescriptor("kotlin.String", PrimitiveKind.STRING)
}
