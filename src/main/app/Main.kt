package app


import app.client.JobsClient
import app.client.SkillsClient
import app.model.FirstResult
import app.model.JobsAutocompleteEntry
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.model.Health
import app.model.SkillsAutocompleteEntry
import app.service.FirstResultService
import app.service.JobsAutocompleteService
import app.service.SkillsAutocompleteService
import io.ktor.features.DefaultHeaders
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.*


fun main(args: Array<String>) {
    val json = Json(JsonConfiguration.Stable)
    val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        routing {
            get("/health") {
                call.respondText(
                        json.stringify(Health.serializer(), Health()),
                        ContentType.Application.Json
                )
            }
            // TODO endpoint test
            post("/jobs/autocomplete") {
                call.respondText(
                        json.stringify(
                                JobsAutocompleteEntry.serializer().list,
                                JobsAutocompleteService().getAutocompleteResults(call.parameters)
                        ),
                        ContentType.Application.Json
                )

            }

            // TODO endpoint test
            post("/skills/autocomplete") {
                call.respondText(
                        json.stringify(
                                SkillsAutocompleteEntry.serializer().list,
                                SkillsAutocompleteService().getAutocompleteResults(call.parameters)
                        ),
                        ContentType.Application.Json
                )

            }
            // TODO endpoint test
            post("/first-result") {
                call.respondText(
                        json.stringify(
                                FirstResult.serializer(),
                                FirstResultService().getFirstResult(call.parameters)
                        ),
                        ContentType.Application.Json
                )

            }
        }
    }
    server.start(wait = true)

}

