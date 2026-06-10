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
import react.ChildrenBuilder

private val scope = MainScope()

@JsModule("./App.css")
@JsNonModule
external val styles: dynamic

// --- Main App ---
val App = FC<Props> {
    val cssModulesPlaceholder = styles
    var newLongURL by useState("")
    var shortUrlResult by useState("")
    var errorMessage by useState("")

    // Main Tree Layout
    renderTitle()
    renderInput(
        currentValue = newLongURL,
        onValueChange = { newValue -> newLongURL = newValue }
    )
    renderButton(onButtonClick = {
        handleSubmit(
            inputUrl = newLongURL,
            setLongUrl = { newLongURL = it },
            setShortUrl = { shortUrlResult = it },
            setError = { errorMessage = it }
        )
    })
    renderLineBreaker()
    renderLineBreaker()
    renderErrorDisplay(message = errorMessage)
    renderSuccessDisplay(shortUrl = shortUrlResult, error = errorMessage)
}



// --- Extracted Business Logic ---
private fun handleSubmit(
    inputUrl: String,
    setLongUrl: (String) -> Unit,
    setShortUrl: (String) -> Unit,
    setError: (String) -> Unit
) {
    if (inputUrl.isNotBlank()) {
        try {
            val dataModel = longUrlDataModel(inputUrl)
            setError("") // Reset error message on a fresh, valid attempt

            scope.launch {
                val response = getShortId(dataModel)
                setShortUrl(response)
                setLongUrl("") // Clear the text input box on success
            }
        } catch (e: Exception) {
            setShortUrl("") // Reset results so old links don't display
            setError("Please enter a valid URL layout (e.g, www.wikipedia.org).")
        }
    }
}


// --- Visual Components ---

// 1. Simple Title Component
private fun ChildrenBuilder.renderTitle() {
    h1 { +"Hello!" }
}

// 2. Simple LineBreaker Component
private fun ChildrenBuilder.renderLineBreaker() {
    br {}
}

// 3. Simple Input Component
private fun ChildrenBuilder.renderInput(
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    input {
        className = ClassName("url-input-field")
        type = web.html.InputType.text
        value = currentValue
        onChange = { event -> onValueChange(event.target.value) }
    }
}

// 4. Simple Action Button Component
private fun ChildrenBuilder.renderButton(onButtonClick: () -> Unit) {
    button {
        className = ClassName("action-button")
        onClick = { onButtonClick() }
        +"Get Shortened Link"
    }
}

// 5. Simple Error Display Component
private fun ChildrenBuilder.renderErrorDisplay(message: String) {
    if (message.isNotBlank()) {
        div {
            p { +message }
        }
    }
}

// 6. Simple Success Display Component
private fun ChildrenBuilder.renderSuccessDisplay(shortUrl: String, error: String) {
    if (shortUrl.isNotBlank() && error.isBlank()) {
        div {
            p { +"Your Shortened Link:" }
            a {
                href = shortUrl
                target = web.window.WindowTarget._blank
                +shortUrl
            }
        }
    }
}
