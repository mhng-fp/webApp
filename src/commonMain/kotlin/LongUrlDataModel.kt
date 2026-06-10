package org.example.backend

import kotlinx.serialization.Serializable

class InvalidUrlException(message: String) : Exception(message)

@Serializable
data class longUrlDataModel(val longUrl: String){
     val sanitizedUrl = cleanUrl(longUrl)

}

private fun cleanUrl(url: String): String {
    val formatted = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "https://$url"
    } else url

    // 1. Instantly reject all raw characters forbidden by strict RFC / java.net.URI
    val forbiddenCharacters = setOf(
        ' ', '[', ']', '^', '{', '}', '<', '>', '|', '\\', '"', '`'
    )

    if (formatted.any { it in forbiddenCharacters }) {
        throw InvalidUrlException("URL contains forbidden RFC characters.")
    }

    return formatted
}



