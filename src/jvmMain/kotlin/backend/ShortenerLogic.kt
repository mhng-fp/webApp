package org.example.backend

class UrlShortener(private val domain: String = "http://localhost:8080/shorten/") {

    // Character pool for Base62 encoding
    private val allowedCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val base = allowedCharacters.length

    // Simulated Database Storage
    val idToUrlMap = mutableMapOf<Long, String>()
    val urlToIdMap = mutableMapOf<String, Long>()
    private var currentId = 100000L

    /**
     * Shortens a given long URL string.
     */
    fun shorten(longUrl: String): String {
        // Return existing shortened token if URL was already processed
        if (urlToIdMap.containsKey(longUrl)) {
            return domain + encodeBase62(urlToIdMap[longUrl]!!)
        }

        // Save entry and increment the counter
        val id = currentId++
        idToUrlMap[id] = longUrl
        urlToIdMap[longUrl] = id

        return domain + encodeBase62(id)
    }

    /**
     * Resolves a shortened URL back to its original form.
     */
    fun resolve(shortUrl: String): String? {
        val id = decodeBase62(shortUrl)
        return idToUrlMap[id]
    }


    /**
     * Converts a unique Base10 database ID into a Base62 alphanumeric string token.
     */
    private fun encodeBase62(id: Long): String {
        var num = id
        val sb = StringBuilder()
        while (num > 0) {
            sb.append(allowedCharacters[(num % base).toInt()])
            num /= base
        }
        return sb.reverse().toString()
    }

    /**
     * Decodes a Base62 string token back into its source database numeric ID.
     */
    private fun decodeBase62(str: String): Long {
        var num = 0L
        for (char in str) {
            num = num * base + allowedCharacters.indexOf(char)
        }
        return num
    }
}
