package scraping

import it.skrape.fetcher.request.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import com.google.gson.GsonBuilder

fun main() {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("http://localhost:3001/restaurants")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        for ((name, value) in response.headers) {
            println("TESTING: $name: $value\n")
        }

        println(response.body!!.string())
    }
}

