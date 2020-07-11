package app.client

import app.model.param.AutocompleteParams
import app.model.client.RelatedJobClientResponse
import app.model.response.SkillsAutocompleteEntry
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking


class SkillsClient : BaseClient<SkillsAutocompleteEntry>() {

    override val BASE_PATH = "/skills"

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
