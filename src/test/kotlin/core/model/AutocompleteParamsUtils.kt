package core.model

import app.model.param.AutocompleteParams
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.hamcrest.core.StringContains

object AutocompleteUtils{
    private val json = Json(JsonConfiguration.Stable)

    fun makeParams(begins_with: String, contains: String, ends_with: String): Map<String, String> {
        //TODO how to map this without hardcoding names
        return mapOf("begins_with" to begins_with,
                "contains" to contains,
                "ends_with" to ends_with)
    }
}

