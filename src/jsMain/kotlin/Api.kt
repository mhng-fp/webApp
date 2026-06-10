import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.backend.longUrlDataModel


val jsonCLient  = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getShortId(longUrl: longUrlDataModel): String {
    val result = jsonCLient.post("/shorten") {
        contentType(ContentType.Application.Json)
        setBody(
            longUrl
        )
    }
    return result.body()
}

