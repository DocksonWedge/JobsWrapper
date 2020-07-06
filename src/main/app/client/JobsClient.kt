package app.client

import app.model.AutocompleteEntry
import app.model.JobsAutocompleteEntry
import app.model.AutocompleteParams
import app.model.SkillsAutocompleteEntry
import io.ktor.client.*
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.getContextual
import kotlinx.serialization.modules.serializersModule
import kotlinx.serialization.serializer


private const val BASE_PATH = "/jobs"

class JobsClient : BaseClient<JobsAutocompleteEntry>(basePath = BASE_PATH)  {

   //TODO - integration test
   override fun autocomplete(begins_with: String, contains: String, ends_with: String): List<JobsAutocompleteEntry> {

        val param =
                AutocompleteParams(begins_with, contains, ends_with)
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
