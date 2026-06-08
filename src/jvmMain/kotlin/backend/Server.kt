package org.example.backend

import ShoppingListDataModel
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.netty.handler.codec.compression.StandardCompressionOptions.gzip
import java.io.File

val shoppingList = mutableListOf(
    ShoppingListDataModel("Orange", 1),
    ShoppingListDataModel("Apple", 2)
)


//URL: http://localhost:9191/shoppingList
fun main() {
        embeddedServer(Netty, port = 9191) {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowMethod(HttpMethod.Get)
                allowMethod(HttpMethod.Post)
                allowMethod(HttpMethod.Delete)
                anyHost()
            }
            install(Compression) {
                gzip()
            }

            routing {
                staticResources("/static", "static")
                route("/shoppingList") {
                    get {
                        val file = File("src/commonMain/resources/index.html")
                        call.respondText(
                            text = file.readText(),
                            ContentType.Text.Html)
                    }
                    post {
                        shoppingList += call.receive<ShoppingListDataModel>()
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
            }.start(wait = true)
}
