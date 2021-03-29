package top.mcwebsite.woxin.ktorsample

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


suspend fun main() {
    embeddedServer(Netty, 8000) {
        install(ContentNegotiation) {
            serialization()
        }
        routing {
            get("/hello") {
                call.respond("Hello Ktor!")
            }
        }
    }.start(true)
}
