package app.client

import app.model.param.AutocompleteParams
import app.model.client.RelatedJobClientResponse
import app.model.response.JobsAutocompleteEntry
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking


class JobsClient : BaseClient<JobsAutocompleteEntry>()  {
    override val BASE_PATH = "/jobs"

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
