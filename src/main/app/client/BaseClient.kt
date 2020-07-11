package app.client

import app.model.response.AutocompleteEntry
import app.model.response.JobsAutocompleteEntry
import app.model.client.RelatedJobClientResponse
import app.model.response.SkillsAutocompleteEntry
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.Serializable



@Serializable
data class AutocompleteEntryWrapper(val ae: AutocompleteEntry)

val autocompleteEntryModule = SerializersModule {
    polymorphic(AutocompleteEntry::class) {
        JobsAutocompleteEntry::class with JobsAutocompleteEntry.serializer()
        SkillsAutocompleteEntry::class with SkillsAutocompleteEntry.serializer()
    }
}
val json = Json(context = autocompleteEntryModule)

abstract class BaseClient<T>(/*val basePath: String , val clazz: Class<T>*/) where T : AutocompleteEntry {
    protected val BASE_URL: String = System.getenv("DATA_AT_WORK_URL")
    protected abstract val BASE_PATH: String
    protected val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    //TODO - can an implementation of this work with generics?
    abstract fun autocomplete(begins_with: String, contains: String, ends_with: String): List<T>

    fun relatedJobs(uuid: String): RelatedJobClientResponse{
        return runBlocking {
            return@runBlocking client.get<RelatedJobClientResponse>(
                    urlString = "$BASE_URL$BASE_PATH/$uuid/related_jobs"
            )
        }
    }
}