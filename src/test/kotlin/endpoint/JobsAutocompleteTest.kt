package endpoint

import core.BaseEndpointTest
import core.model.AutocompleteUtils
import io.kotest.inspectors.forAll
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.PropTestConfig
import io.kotest.property.arbitrary.element
import io.kotest.property.exhaustive.collection
import io.kotest.property.exhaustive.times
import io.kotest.property.forAll
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.*
import kotlin.random.Random
import org.hamcrest.Matchers.hasLength


class JobsAutocompleteTest
    : BaseEndpointTest({
    val url = "$BASE_URL/jobs/autocomplete"

    "Sanity: Retrieves jobs autocomplete with begins_with == assistant" - {
        //TODO why doesn't output show when passing? Works when running all tests, but not individually.

        val response = given()
                .queryParams(AutocompleteUtils.makeParams("assistant", "b", "c"))
                .post(url)

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("[1].normalized_job_title", startsWith("a"))
                .body("[7].suggestion", startsWith("Assistant"))
                .body("[12].parent_uuid", hasLength(32))
                .body("[9].uuid", hasLength(32))
    }


    val invalidParams = listOf(
            "abcedfg",
            "~`!@#\$%^&*()_+-=1234567890{}[]|\\:\"';:<>?,./",
            "the quick brown fox jumps over the lazy dog")
    val validParams = listOf(
            "bank",
            "assistant",
            "manager")

    "Property: Autocompletes the first given parameter to real job names. If not found returns all jobs." - {
        forAll(3, //PropTestConfig(0) leave this low to avoid hammering use prop test config for seed
                Exhaustive.collection(listOf(
                        "",
                        validParams[0],
                        invalidParams[0])),
                Arb.element(listOf(
                        "   ",
                        invalidParams[1],
                        validParams[1])),
                Arb.element(listOf(
                        "NULL",
                        invalidParams[2],
                        validParams[2])))
        { begins_with, contains, ends_with ->

            callAndAssertJobsAutocomplete(begins_with, contains, ends_with, url, validParams)
            true
        }
    }


    // TODO the result output for this makes no sense...
    "Testing style - Property: Autocompletes the first given parameter to real job names. If not found returns all jobs." - {
        //todo - we could extract the loop and logic into 2 pieces and mix and match
        listOf("", validParams[0], invalidParams[0])
                .forAll { begins_with ->
                    listOf("   ", invalidParams[1], validParams[1])
                            .forAll { contains ->
                                listOf("NULL", invalidParams[2], validParams[2])
                                        .forAll { ends_with ->
                                            callAndAssertJobsAutocomplete(begins_with, contains, ends_with, url, validParams)
                                        }
                            }
                }
    }


})

private fun callAndAssertJobsAutocomplete(begins_with: String, contains: String, ends_with: String, url: String, validParams: List<String>) {
    val response = given()
            .queryParams(AutocompleteUtils.makeParams(begins_with, contains, ends_with))
            .post(url)

    val rowToCheck = Random.nextInt(10)

    when {
        hasParamValue(begins_with) -> {
            if (validParams.contains(begins_with)) {
                response.then().assertThat().statusCode(200)
                        .body("[$rowToCheck].normalized_job_title", startsWith(begins_with))
            } else {
                response.then().assertThat().statusCode(404)
            }
        }
        hasParamValue(contains) -> {
            if (validParams.contains(contains)) {
                response.then().assertThat().statusCode(200)
                        .body("[$rowToCheck].normalized_job_title", containsString(contains))
            } else {
                response.then().assertThat().statusCode(404)
            }
        }
        hasParamValue(ends_with) -> {
            if (validParams.contains(ends_with)) {
                response.then().assertThat().statusCode(200)
                        .body("[$rowToCheck].normalized_job_title", endsWith(ends_with))
            } else {
                response.then().assertThat().statusCode(404)
            }
        }
        else -> {
            response.then().assertThat().statusCode(400)
        }
    }
}

private inline fun hasParamValue(value: String): Boolean {
    return value.isNotBlank() && value != "NULL"
}
