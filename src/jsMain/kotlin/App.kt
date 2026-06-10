import react.useState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.example.backend.longUrlDataModel
import react.Props
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import web.html.text
import web.window._blank
import web.cssom.ClassName

private val scope = MainScope()

@JsModule("./App.css")
@JsNonModule
external val styles: dynamic

val App = FC<Props> {
    val cssModulesPlaceholder = styles
    var newLongURL by useState("")
    var shortUrlResult by useState("")
    var errorMessage by useState("")

    h1 {
        +"Hello!"
    }

    // 1. Input Form
    input {
        className = ClassName("url-input-field")
        type = web.html.InputType.text
        value = newLongURL
        onChange = { event ->
            newLongURL = event.target.value
        }
    }

    // 2. The Action Button
    button {
        +"Get Shortened Link"
        onClick = {
            if (newLongURL.isNotBlank()) {
                try {
                    // Attempt validation/instantiation
                    val dataModel = longUrlDataModel(newLongURL)
                    errorMessage = "" // Reset error message on a fresh, valid attempt

                    scope.launch {
                        val response = getShortId(dataModel)
                        shortUrlResult = response
                    }
                } catch (e: Exception) {
                    // Catch local URL validation errors specifically
                    shortUrlResult = "" // Reset results so old links don't display
                    errorMessage = "Please enter a valid URL layout (e.g, www.wikipedia.org)."
                }
            }
        }
    }

    br {}
    br {}

    // 3. Error Container UI
    if (errorMessage.isNotBlank()) {
        div {
            p {
                +errorMessage
            }
        }
    }

    // 4. Result container display
    if (shortUrlResult.isNotBlank() && errorMessage.isBlank()) {
        div {
            p {
                +"Your Shortened Link:"
            }

            a {
                href = shortUrlResult
                target = web.window.WindowTarget._blank
                +shortUrlResult
            }
        }
    }
}
