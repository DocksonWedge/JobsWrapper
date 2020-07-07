package app.service

import app.model.AutocompleteEntry
import app.model.FirstResult
import app.model.JobsAutocompleteEntry
import app.model.SkillsAutocompleteEntry
import io.ktor.http.Parameters

class FirstResultService {
    // TODO dependency injection?
    private val jobsAutocompleteService = JobsAutocompleteService()
    private val skillsAutocompleteService = SkillsAutocompleteService()

    // TODO unit test
    fun getFirstResult(params: Parameters) : FirstResult{
        val resultObject : FirstResult
        val jobs = jobsAutocompleteService.getAutocompleteResults(params)
        val skills = skillsAutocompleteService.getAutocompleteResults(params)
        return findAlphaFirst(jobs, skills)
    }
    // TODO unit test
    private fun findAlphaFirst(jobs: List<JobsAutocompleteEntry>, skills: List<SkillsAutocompleteEntry>)  : FirstResult {

        val job = transformAutocompleteEntry(jobs[0])
        val skill = transformAutocompleteEntry(skills[0])
        if (job.normalized_title <= skill.normalized_title){
            return job
        } else {
            return skill
        }
     }
    // TODO use lambdas for builders? https://kotlinlang.org/docs/reference/lambdas.html
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: JobsAutocompleteEntry) : FirstResult{
        var result = transformAutocompleteEntry(entry as AutocompleteEntry)
        result.normalized_title = entry.normalized_job_title
        return result
    }
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: SkillsAutocompleteEntry) : FirstResult{
        var result = transformAutocompleteEntry(entry as AutocompleteEntry)
        result.normalized_title = entry.normalized_skill_name
        return result
    }
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: AutocompleteEntry): FirstResult {
        return FirstResult("",entry.uuid, entry.suggestion)
    }
}