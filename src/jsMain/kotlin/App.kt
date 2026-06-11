import react.useState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.example.backend.longUrlDataModel
import react.Props
import react.FC
import react.dom.html.ReactHTML.a
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
    renderResultDisplay(shortUrl = shortUrlResult, error = errorMessage)
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
    h1 { +"Enter your link" }
}

// 2. Simple Input Component
private fun ChildrenBuilder.renderInput(
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    input {
        className = ClassName("url-input-field")
        type = web.html.InputType.text
        value = currentValue
        placeholder = "https://example.com"
        onChange = { event -> onValueChange(event.target.value) }
    }
}

// 3. Simple Action Button Component
private fun ChildrenBuilder.renderButton(onButtonClick: () -> Unit) {
    button {
        className = ClassName("action-button")
        onClick = { onButtonClick() }
        +"Submit"
    }
}

// 4. Simple Result Display Component
private fun ChildrenBuilder.renderResultDisplay(shortUrl: String, error: String) {
    val hasError = error.isNotBlank()
    val hasSuccess = shortUrl.isNotBlank() && !hasError
    val isVisible = hasError || hasSuccess

    div {
        // Keeps container layout footprint alive, appends "show" if ready
        className = ClassName(if (isVisible) "result-container show" else "result-container")

        when {
            hasError -> {
                p {
                    className = ClassName("error-text")
                    +error
                }
            }
            hasSuccess -> {
                p { +"Your Shortened Link:" }
                a {
                    href = shortUrl
                    target = web.window.WindowTarget._blank
                    +shortUrl
                }
            }
        }
    }
}
