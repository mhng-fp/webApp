import react.create
import react.dom.client.createRoot
import web.dom.ElementId
import web.dom.document

fun main() {
    val container = document.getElementById(elementId = ElementId("root")) ?: error ("could not find container")
    createRoot(container).render(App.create())
}


