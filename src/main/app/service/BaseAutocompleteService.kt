package app.service

import app.client.BaseClient
import app.model.response.AutocompleteEntry
import io.ktor.http.Parameters

abstract class BaseAutocompleteService<T>(val client: BaseClient<T> )  where T : AutocompleteEntry {
    // TODO: unit test?Skip since just a pass-through?
    fun getAutocompleteResults(params: Parameters): List<T> {
        return client.autocomplete(
                params["begins_with"] ?: "",
                params["contains"] ?: "",
                params["ends_with"] ?: ""
        )
    }
}