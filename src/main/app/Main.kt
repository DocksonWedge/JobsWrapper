package app


import app.model.response.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.service.FirstResultService
import app.service.JobsAutocompleteService
import app.service.RelatedJobService
import app.service.SkillsAutocompleteService
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.ServerResponseException
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.util.rootCause
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.*
import java.lang.IllegalArgumentException


fun main(args: Array<String>) {
    val json = Json(JsonConfiguration.Stable)
    val server = embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(StatusPages) {
            exception<IllegalArgumentException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause.localizedMessage )
            }
            exception<ClientRequestException> { cause ->
                call.respond( cause.response.status )
            }
            // TODO -> verify with mocks we correctly forward 5** errors!
            exception<ServerResponseException> { cause ->
                call.respond( cause.response.status )
            }
        }

        routing {
            get("/health") {
                call.respondText(
                        json.stringify(Health.serializer(), Health()),
                        ContentType.Application.Json
                )
            }
            // These are posts to allow for testing different types, they should really be GETs
            //TODO don't return 500 for no request body
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
            // TODO return 201
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
            get("/first_result") {
                call.respondText(
                        json.stringify(
                                FirstResult.serializer(),
                                FirstResultService().getFirstResult(call.parameters)
                        ),
                        ContentType.Application.Json
                )
            }
            //TODO - Test comparing to actual response from DAW
            get("/related_jobs"){
                call.respondText(
                        json.stringify(
                                RelatedJobResponseEntry.serializer().list,
                                RelatedJobService().getRelatedJobs(call.parameters)
                        ),
                        ContentType.Application.Json
                )
            }

        }
    }
    server.start(wait = true)

}

