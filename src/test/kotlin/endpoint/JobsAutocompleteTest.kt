package endpoint

import core.BaseEndpointTest
import core.model.AutocompleteUtils
import io.restassured.RestAssured.given
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.random.Random
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.Matchers.hasLength


object JobsAutocompleteTest
    : BaseEndpointTest(
        "/jobs/autocomplete", {

    val json = Json(JsonConfiguration.Stable)

    "Retrieves jobs autocomplete with begins_with == assistant" {
        println("start JobsAutocompleteTest")
        //TODO why doesn't output show when passing? Works when running all tests, but not individually.


            var response = given()
                    .queryParams(
                            AutocompleteUtils.makeParams("assistant", "b", "c")
                    )
                    .post("$BASE_URL/jobs/autocomplete")

                "We receive a list of jobs"{
                    response.then().assertThat()
                            .statusCode(200)
                            .and()
                            .body("[1].normalized_job_title", startsWith("a"))
                            .body("[7].suggestion", startsWith("Assistant"))
                            .body("[12].parent_uuid", hasLength(32))
                            .body("[9].uuid", hasLength(32))
                }

            Thread.sleep(Random.nextLong(1000))
            println("end JobsAutocompleteTest")

    }
})