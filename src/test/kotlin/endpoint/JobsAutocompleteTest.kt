package endpoint

import core.BaseEndpointTest
import core.model.AutocompleteUtils
import io.restassured.RestAssured.given
import kotlinx.serialization.json.*
import org.spekframework.spek2.style.specification.describe
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.text.CharSequenceLength



object JobsAutocompleteTest : BaseEndpointTest({
    val json = Json(JsonConfiguration.Stable)

    //TODO why doesn't output show when passing? Works when running all tests, but not individually.
    describe("Working /jobs/autocomplete") {

        var response = given()
                .queryParams(
                        AutocompleteUtils.makeParams("assistant", "b", "c")
                )
                .post("$BASE_URL/jobs/autocomplete")

        it("Receives a 'success' response") {
            response.then().assertThat()
                    .statusCode(200)
                    .body("[1].normalized_job_title", startsWith("assistant"))
                    .body("[7].suggestion", startsWith("Assistant"))
                    .body("[12].parent_uuid", CharSequenceLength(equalTo(32)))
                    .body("[9].uuid", CharSequenceLength(equalTo(32)))
        }
    }
})