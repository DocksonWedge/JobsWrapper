package endpoint

import core.BaseEndpointTest
import io.restassured.RestAssured.get
import kotlin.random.Random

//TODO running multiple times?
object HealthCheckTest : BaseEndpointTest("/heatlh", {
    //TODO investigate?
    //RestAssured.rootPath
    //TODO why doesn't output show when passing? Works when running all tests, but not individually.
    "Working healthcheck" {
        println("start HealthCheckTest")
        var response = get("$BASE_URL/health")

        "Receives a 'success' response" {
            response.then().assertThat()
                    .statusCode(200)
        }
        Thread.sleep(Random.nextLong(1000))
        println("end HealthCheckTest")
    }
})