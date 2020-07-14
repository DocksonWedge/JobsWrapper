package app.service

import app.enum.ResultType
import app.model.response.AutocompleteEntry
import app.model.response.FirstResult
import app.model.response.JobsAutocompleteEntry
import app.model.response.SkillsAutocompleteEntry
import io.ktor.http.Parameters

class FirstResultService {
    // TODO dependency injection?
    private val jobsAutocompleteService = JobsAutocompleteService()
    private val skillsAutocompleteService = SkillsAutocompleteService()

    // TODO unit test
    fun getFirstResult(params: Parameters) : FirstResult {
        val resultObject : FirstResult
        val jobs = jobsAutocompleteService.getAutocompleteResults(params)
        val skills = skillsAutocompleteService.getAutocompleteResults(params)
        return findAlphaFirst(jobs, skills)
    }
    // TODO unit test
    private fun findAlphaFirst(jobs: List<JobsAutocompleteEntry>, skills: List<SkillsAutocompleteEntry>)  : FirstResult {
        // TODO use async for double call?
        val job = transformAutocompleteEntry(jobs[0])
        val skill = transformAutocompleteEntry(skills[0])
        return if (job.normalized_title <= skill.normalized_title){
            job
        } else {
            skill
        }
     }
    // TODO use lambdas for builders? https://kotlinlang.org/docs/reference/lambdas.html
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: JobsAutocompleteEntry) : FirstResult {
        var result = transformAutocompleteEntry(entry as AutocompleteEntry)
        result.normalized_title = entry.normalized_job_title
        result.type = ResultType.JOB
        return result
    }
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: SkillsAutocompleteEntry) : FirstResult {
        var result = transformAutocompleteEntry(entry as AutocompleteEntry)
        result.normalized_title = entry.normalized_skill_name
        result.type = ResultType.SKILL
        return result
    }
    // TODO unit test
    private inline fun transformAutocompleteEntry(entry: AutocompleteEntry): FirstResult {
        return FirstResult("", entry.uuid, entry.suggestion, ResultType.JOB)
    }
}