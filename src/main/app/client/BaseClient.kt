package app.client

import app.model.AutocompleteEntry
import app.model.JobsAutocompleteEntry
import app.model.SkillsAutocompleteEntry
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
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

abstract class BaseClient<T>(val basePath: String /*, val clazz: Class<T>*/) where T : AutocompleteEntry {
    protected val BASE_URL: String = System.getenv("DATA_AT_WORK_URL")
    protected val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    //TODO - can an implementation of this work with generics?
    abstract fun autocomplete(begins_with: String, contains: String, ends_with: String): List<T>
}