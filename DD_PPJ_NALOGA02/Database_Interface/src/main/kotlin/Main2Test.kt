package scraping

import it.skrape.fetcher.request.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import scraper.Menu
import scraper.Restaurant
import scraper.RestaurantList
import util.DatabaseJsonToClass
import javax.xml.crypto.Data


fun main() {
    val client = OkHttpClient()

    var request = Request.Builder()
        .url("http://localhost:3001/restaurants")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println ("=================================RESTAURANTS=================================")
        val responseBody = response.body!!.string()

        // Parse the JSON array into individual JSON objects and store them as strings in an array
        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        // Now stringList is an array of strings, each string is a JSON object
        stringList.forEach {
            DatabaseJsonToClass.JsonToRestaurantClass(it)
//            val jsonElement = JsonParser.parseString(it)
//            val prettyJson = GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
//            println(prettyJson)
        }
    }

    request = Request.Builder()
        .url("http://localhost:3001/menus")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println ("=================================MENUS=================================")
        val responseBody = response.body!!.string()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        // Now stringList is an array of strings, each string is a JSON object
        stringList.forEach {
            DatabaseJsonToClass.JsonToMenuItem(it)
//            val jsonElement = JsonParser.parseString(it)
//            val prettyJson = GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
//            println(prettyJson)
        }
    }

//    request = Request.Builder()
//        .url("http://localhost:3001/tags")
//        .build()
//
//    client.newCall(request).execute().use { response ->
//        if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//        println ("=================================TAGS=================================")
//        val responseBody = response.body!!.string()
//        val jsonElement = JsonParser.parseString(responseBody)
//        val prettyJson = GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
//        println(prettyJson)
//    }

    for (restaurant in RestaurantList.restaurants) {
        println(restaurant)
    }
}

