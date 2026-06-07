import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*


val jsonCLient  = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getShoppingList(): List<ShoppingListDataModel> {
    return jsonCLient.get("/shoppingList").body()
}

suspend fun addShoppingList(shoppingListItem: ShoppingListDataModel) {
    jsonCLient.post("/shoppingList") {
        contentType(ContentType.Application.Json)
        setBody(
            shoppingListItem
        )
    }
}

