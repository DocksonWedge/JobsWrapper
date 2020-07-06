package app.client

import app.model.AutocompleteEntry
import app.model.AutocompleteParams
import app.model.JobsAutocompleteEntry
import app.model.SkillsAutocompleteEntry
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.serializersModule
import kotlinx.serialization.serializer


@Serializable
data class AutocompleteEntryWrapper(val ae: AutocompleteEntry)

val autocompleteEntryModule = SerializersModule {
    polymorphic(AutocompleteEntry::class) {
        JobsAutocompleteEntry::class with JobsAutocompleteEntry.serializer()
        SkillsAutocompleteEntry::class with SkillsAutocompleteEntry.serializer()
    }
}
val json = Json(context = autocompleteEntryModule)

abstract class BaseClient<T>(val basePath: String /*, val clazz: Class<T>*/ ) where T : AutocompleteEntry {

    // TODO: Dependency injection?
    private val BASE_URL = System.getenv("DATA_AT_WORK_URL")

    private val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer ()
        }
    }

    //TODO - can this work with generics?
    open fun <T> baseAutocomplete(begins_with: String, contains: String, ends_with: String): List<T> {
        val param =
                AutocompleteParams(begins_with, contains, ends_with)
                        .getEffectiveParam()
        return runBlocking {
            return@runBlocking client.get<List<T>>(
                    urlString = "$BASE_URL$basePath/autocomplete",
                    block = {
                        parameter(param.first, param.second)
                    }
            )
        }
    }
}