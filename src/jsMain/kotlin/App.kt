import react.useState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.Props
import react.useEffectOnce
import react.FC
import react.dom.html.ReactHTML.h1

private val scope = MainScope()


val App = FC<Props>{
    var shoppingList by useState(emptyList<ShoppingListDataModel>())

    useEffectOnce {
        scope.launch {
            shoppingList = getShoppingList()
        }
    }

    h1 {
        +"Full ShoppingList"
    }
}

