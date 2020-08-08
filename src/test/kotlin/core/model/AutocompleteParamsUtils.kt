package core.model

import app.model.param.AutocompleteParams
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.hamcrest.core.StringContains

object AutocompleteUtils {
    private val json = Json(JsonConfiguration.Stable)

    fun makeParams(begins_with: String, contains: String, ends_with: String): Map<String, String> {
        val params = HashMap<String, String>()
        if (begins_with != "NULL")  params["begins_with"] = begins_with
        if (contains != "NULL") params["contains"] = contains
        if (ends_with != "NULL") params["ends_with"] = ends_with

        return params
    }
}

