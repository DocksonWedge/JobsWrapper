package app.service

import app.client.JobsClient
import app.model.response.JobsAutocompleteEntry
import org.koin.dsl.module

val jobsAutocompleteModule = module {
    single { JobsAutocompleteService() }
}

class JobsAutocompleteService() : BaseAutocompleteService<JobsAutocompleteEntry>(client = JobsClient()) {


}