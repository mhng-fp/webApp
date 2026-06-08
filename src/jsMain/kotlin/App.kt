import react.useState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.Props
import react.useEffectOnce
import react.FC
import react.Key
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import web.html.text

private val scope = MainScope()


val App = FC<Props>{
    var shoppingList by useState(emptyList<ShoppingListDataModel>())
    var newItemName by useState("") // Tracks what the user types in the input box

    useEffectOnce {
        scope.launch {
            shoppingList = getShoppingList()
        }
    }

    h1 {
        +"Full ShoppingList"
    }

    // 1. Render the current list of items
    ul {
        shoppingList.sortedByDescending(ShoppingListDataModel::priority).forEach { item ->
            li{
                key = Key("${item.id}")
                + "[${item.priority}] ${item.desc}"
            }
        }
    }

    // 2. Add an Input Form
    input {
        type = web.html.InputType.text
        placeholder = "Add new item..."
        value = newItemName
        onChange = { event ->
            newItemName = event.target.value
        }
    }

    // 3. Add the Action Button
    button {
        +"Add Item"
        onClick = {
            if (newItemName.isNotBlank()) {
                scope.launch {
                    // addShoppingListItem(newItemName)
                    val priorityToInject = (shoppingList.lastOrNull()?.priority ?: 0) + 1
                    postShoppingList(ShoppingListDataModel(newItemName, priorityToInject) )

                    // Refresh the list after adding
                    shoppingList = getShoppingList()
                    newItemName = "" // Clear the text input box
                }
            }
        }
    }
}

