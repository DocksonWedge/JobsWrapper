package core

import io.kotest.core.spec.style.StringSpec
import org.junit.jupiter.api.Disabled



//TODO base autocomplete endpoint test mid layer
//TODO do I need to explicitly pass the stringspec param
@Disabled
abstract class BaseEndpointTest(path : String, spec : StringSpec.() -> Unit ) : StringSpec(spec) {
    companion object Constants{
        const val BASE_URL = "http://127.0.0.1:8080"
    }
    val URL = "$BASE_URL$path"
}