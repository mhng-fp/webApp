package org.example.backend

import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
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

val shortener = UrlShortener()

//URL to hit: http://localhost:8080/
fun main() {
        embeddedServer(Netty, port = 8080) {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowMethod(HttpMethod.Get)
                allowMethod(HttpMethod.Post)
                anyHost()
            }
            install(Compression) {
                gzip()
            }

            routing {
                staticResources("/static", "static")
                get("/"){
                    val file = File("src/commonMain/resources/index.html")
                    call.respondText(
                        text = file.readText(),
                        ContentType.Text.Html)
                }
                get("/urlToIdMap") {
                    call.respond(shortener.urlToIdMap)
                }
                get("/idToUrlMap") {
                    call.respond(shortener.idToUrlMap)
                }
                route("/shorten") {
                    post {
                        val longUrl = call.receive<longUrlDataModel>()
                        val shortId= shortener.shorten(longUrl.sanitizedUrl)
                        call.respond(shortId)
                    }
                    get("/{id}") {
                        val shortId = call.parameters["id"] ?: ""
                        val longUrl = shortener.resolve(shortId) ?: ""
                        call.respondRedirect(longUrl, permanent = false)
                    }
                }
            }
            }.start(wait = true)
}
