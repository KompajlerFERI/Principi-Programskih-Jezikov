package util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import scraper.Restaurant
import java.io.IOException

data class RestaurantDb(
    val name: String,
    val address: String,
    val mealPrice: String,
    val mealSurcharge: String,
    val workingHours: List<WorkingHour>,
    val ownerId: String,
    val location: Location
)

data class WorkingHour(
    val day: String,
    val open: String,
    val close: String
)

data class LoginResponse(
    val token: String
)

data class Location(
    val type: String,
    val coordinates: List<String>
)

object Auth {
    var cookie: String = ""

    fun login(username: String, password: String) {
        val loginRequestBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()

        val loginRequest = Request.Builder()
            .url("http://127.0.0.1:3001/users/login")
            .post(loginRequestBody)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Accept", "application/json")
            .build()

        val client = OkHttpClient()
        client.newCall(loginRequest).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body!!.string()
            println("Login response body: $responseBody")
            val loginResponse = Gson().fromJson(responseBody, LoginResponse::class.java)
            //token = loginResponse.token
            cookie = response.headers["Set-Cookie"].toString()
        }
    }
}

object PushToDatabase {

    fun pushRestaurant(restaurant: Restaurant) {
        if (restaurant.isInDatabase) {
            println("Restaurant already exists in the database.")
            return
        }
        Auth.login("Sluzek", "1234")

        val restaurantDb = RestaurantDb(
            name = restaurant.name,
            address = restaurant.address,
            mealPrice = restaurant.fullPrice.replace(",", "."),
            mealSurcharge = restaurant.payPrice.replace(",", "."),
            workingHours = restaurant.workingTimes.map {
                val split = it.split(" : ")
                if (split[1] == "Zaprto") {
                    WorkingHour(split[0], "Zaprto", "Zaprto")
                } else {
                    val times = split[1].split(" - ")
                    WorkingHour(split[0], times[0], times[1])
                }
            },
            ownerId = "6654c15b6afe60954c9946fd",
            location = Location(
                type = "Point",
                coordinates = listOf(restaurant.longitude, restaurant.latitude)
            )
        )

        //println(restaurantDb)
        val JSON = "application/json".toMediaType()
        val gson = GsonBuilder().create()
        val json = gson.toJson(restaurantDb)

        val body = json.toRequestBody(JSON)

        val request = Request.Builder()
            .url("http://localhost:3001/restaurants")
            .post(body)
            //.addHeader("Authorization", "Bearer ${Auth.token}")
            .addHeader("Cookie", Auth.cookie)
            .build()

        val client = OkHttpClient()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            val responseBody = response.body!!.string()
            println("Response body: " + responseBody)
            restaurant.isInDatabase = true

            restaurant.id = DatabaseJsonToClass.getRestaurantId(responseBody)
            println("RESTAURANT ID: ${restaurant.id}")
        }
    }
}