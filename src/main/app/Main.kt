package main.kotlin


import app.client.JobsClient
import app.model.JobsAutocompleteEntry
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.model.Health
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.*

fun main(args: Array<String>) {
    val json = Json(JsonConfiguration.Stable)
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/health") {
                call.respondText(
                        json.stringify(Health.serializer(), Health()),
                        ContentType.Application.Json
                )
            }
            // TODO endpoint test
            post("/jobs/autocomplete") {
                val params = call.parameters
                call.respondText(
                        json.stringify(JobsAutocompleteEntry.serializer().list,
                                JobsClient.autocomplete(
                                        params["begins_with"] ?: "",
                                        params["contains"] ?: "",
                                        params["ends_with"] ?: ""
                                )
                        ),
                        ContentType.Application.Json
                )

            }

            // TODO endpoint test
            post("/skills/autocomplete") {
                val params = call.parameters
                call.respondText(
                        json.stringify(JobsAutocompleteEntry.serializer().list,
                                JobsClient.autocomplete(
                                        params["begins_with"] ?: "",
                                        params["contains"] ?: "",
                                        params["ends_with"] ?: ""
                                )
                        ),
                        ContentType.Application.Json
                )

            }
        }
    }
    server.start(wait = true)
}

