package endpoint

import io.restassured.RestAssured.get
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object HealthCheckTest : Spek({

    //TODO move to environment variable
    val BASE_URL = "http://127.0.0.1:8080"
    //TODO why doesn't output show when passing? Works when running all tests, but not individually.
    describe("Working healthcheck") {
        var response = get("$BASE_URL/health")
        it("Receives a 'success' response") {
            response.then().assertThat()
                    .statusCode(200)
        }
    }
})