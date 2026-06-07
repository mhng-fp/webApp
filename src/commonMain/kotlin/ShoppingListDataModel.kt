import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListDataModel(var desc: String, val priority: Int) {
    val id: Int = desc.hashCode()

}