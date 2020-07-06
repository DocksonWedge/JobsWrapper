package app.service

import app.client.BaseClient
import app.client.JobsClient
import app.model.AutocompleteEntry
import app.model.JobsAutocompleteEntry
import io.ktor.http.Parameters
import org.koin.dsl.module

val jobsAutocompleteModule = module {
    single { JobsAutocompleteService() }
}

class JobsAutocompleteService() : BaseAutocompleteService<JobsAutocompleteEntry>(client = JobsClient()) {


}