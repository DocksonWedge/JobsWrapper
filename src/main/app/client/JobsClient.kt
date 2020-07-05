package app.client

import app.model.JobsAutocompleteEntry
import app.model.AutocompleteParams
import io.ktor.client.*
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking

object JobsClient {

    private val BASE_PATH = "/jobs"
    private val BASE_URL = System.getenv("DATA_AT_WORK_URL")

    private val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    //TODO - integration test
    fun autocomplete(begins_with: String, contains: String, ends_with: String): List<JobsAutocompleteEntry> {
        val param = AutocompleteParams(begins_with, contains, ends_with)
                        .getEffectiveParam()
        return runBlocking {
            return@runBlocking client.get<List<JobsAutocompleteEntry>>(
                    urlString = "$BASE_URL$BASE_PATH/autocomplete",
                    block = {
                        parameter(param.first, param.second)
                    }
            )
        }
    }
}
