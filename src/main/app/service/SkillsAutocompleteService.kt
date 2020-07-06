package app.service

import app.client.SkillsClient
import app.model.SkillsAutocompleteEntry
import org.koin.dsl.module

val skillsAutocompleteModule = module {
    single { SkillsAutocompleteService() }
}

class SkillsAutocompleteService() : BaseAutocompleteService<SkillsAutocompleteEntry>(client = SkillsClient()) {

}