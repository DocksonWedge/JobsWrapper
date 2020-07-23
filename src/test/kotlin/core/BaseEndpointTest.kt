package core

import io.kotest.core.spec.style.StringSpec
import org.junit.jupiter.api.Disabled



//TODO base autocomplete endpoint test mid layer
//TODO do I need to explicitly pass the stringspec param
//TODO make work with REST tests
@Disabled
abstract class BaseEndpointTest( spec : StringSpec.() -> Unit ) : StringSpec(spec) {
    companion object Constants{
        const val BASE_URL = "http://127.0.0.1:8080"
    }
}