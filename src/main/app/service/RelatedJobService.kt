package app.service

import app.client.JobsClient
import app.client.SkillsClient
import app.enum.ResultType
import app.model.response.RelatedJobResponseEntry
import io.ktor.http.Parameters

class RelatedJobService {
    fun getRelatedJobs(params: Parameters): List<RelatedJobResponseEntry> {
        val uuid = params["uuid"] ?: ""
        val type = ResultType.valueOf(
                (params["type"] ?: "SKILL")
                        .toUpperCase()
        )
        val client =
                if (type == ResultType.JOB) {
                    JobsClient()
                } else {
                    SkillsClient()
                }
        // TODO - get unusual jobs late?
        val response = client
                .relatedJobs(uuid)
                .related_job_titles

        return response.map { entry ->
            RelatedJobResponseEntry(
                    uuid = entry.uuid,
                    type = type,
                    title = entry.title
            )
        }
    }
}