import react.useState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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

// This line tells Webpack to fetch the file from your resources folder
@JsModule("./App.css")
@JsNonModule
external val styles: dynamic

val App = FC<Props> {
    val cssModulesPlaceholder = styles
    var newLongURL by useState("") // Tracks what the user types in the input box
    var shortUrlResult by useState("")

    h1 {
        +"Hello!"
    }

    // 1. Add an Input Form
    input {
        className = ClassName("url-input-field")
        type = web.html.InputType.text
        value = newLongURL
        onChange = { event ->
            newLongURL = event.target.value
        }
    }

    // 2. Add the Action Button
    button {
        +"Get Shortened Link"
        onClick = {
            if (newLongURL.isNotBlank()) {
                scope.launch {
                    val response = getShortId(longUrlDataModel(newLongURL))
                    shortUrlResult = response
                    newLongURL = "" // Clear the text input box
                }
            }
        }
    }

    br {}
    br {}

    // 3. Conditionally display the result container ONLY when shortUrlResult is not empty
    if (shortUrlResult.isNotBlank()) {
        div {
            p {
                +"Your Shortened Link:"
            }

            // Display the short URL inside a clickable anchor link tag
            a {
                href = shortUrlResult
                target = web.window.WindowTarget._blank // Opens link in a new tab
                +shortUrlResult
            }
        }
    }
}
