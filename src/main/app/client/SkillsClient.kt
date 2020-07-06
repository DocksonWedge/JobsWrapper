package app.client

import app.model.AutocompleteParams
import app.model.JobsAutocompleteEntry
import app.model.SkillsAutocompleteEntry
import io.ktor.client.*
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking

private const val BASE_PATH = "/skills"

class SkillsClient : BaseClient<SkillsAutocompleteEntry>(basePath = BASE_PATH) {

    //TODO - integration test
    override fun autocomplete(begins_with: String, contains: String, ends_with: String): List<SkillsAutocompleteEntry> {
        val param =
                AutocompleteParams(begins_with, contains, ends_with)
                        .getEffectiveParam()
        return runBlocking {
            return@runBlocking client.get<List<SkillsAutocompleteEntry>>(
                    urlString = "$BASE_URL$BASE_PATH/autocomplete",
                    block = {
                        parameter(param.first, param.second)
                    }
            )
        }
    }
}
