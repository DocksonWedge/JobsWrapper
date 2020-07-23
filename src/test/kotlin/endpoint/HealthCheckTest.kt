package endpoint

import core.BaseEndpointTest
import io.restassured.RestAssured.get
import kotlin.random.Random

//TODO running multiple times?
class HealthCheckTest : BaseEndpointTest({
    //TODO Why does health check test take 1 second?
    //TODO why doesn't output show when passing? Works when running all tests, but not individually.
    "Working healthcheck" {
        var response = get("$BASE_URL/health")

        response.then().assertThat()
                .statusCode(200)

    }
})