package util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import scraper.Menu
import scraper.Restaurant
import scraper.TagList
import scraper.User
import java.io.IOException
import java.util.*

data class RestaurantDb(
    val name: String,
    val address: String,
    val mealPrice: String,
    val mealSurcharge: String,
    val workingHours: List<WorkingHour>,
    val ownerId: String,
    val location: Location,
    val tags: List<String>
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

data class MenuDb(
    val dish: String,
    val sideDishes: List<String>,
    val restaurantId: String,
    val tag: String,
)

data class Tag(
    val tagId: String
)

data class UpdateUserDB(
    val id: String
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

fun getIdFromTagName(tagName: String): String {
    for (tag in TagList.tags) {
        if (tag.name == tagName) {
            return tag.id
        }
    }
    return ""
}



object PushToDatabase {
    fun approveUser(user: User) {
        if (user.pendingApproval == "false") {
            println("User is already approved.")
            return
        }
        Auth.login("Sluzek", "1234")

        val userDb = UpdateUserDB(
            id = user.id
        )

        val JSON = "application/json".toMediaType()
        val gson = GsonBuilder().create()
        val json = gson.toJson(userDb)

        val body = json.toRequestBody(JSON)

        val request = Request.Builder()
            .url("http://localhost:3001/users/approveRestaurantOwner/${user.id}")
            .put(body)
            .addHeader("Cookie", Auth.cookie)
            .build()

        val client = OkHttpClient()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            val responseBody = response.body!!.string()
            println("Response body: " + responseBody)

            user.pendingApproval = "false"
        }
    }
    fun getCategoriesFromMenus(restaurant: Restaurant): MutableList<String> {
        val returnList: MutableList<String> = mutableListOf()

        for (tag in restaurant.menuList) {
            if (!(tag.category in returnList)) {
                returnList.add(tag.category)
            }
        }

        return returnList
    }

    fun pushRestaurant(restaurant: Restaurant) {
        if (restaurant.isInDatabase && restaurant.edited) {
            Auth.login("Sluzek", "1234")

            val tagList = getCategoriesFromMenus(restaurant)
            val tagListIds: MutableList<String> = mutableListOf()

            for (tag in tagList) {
                var category = tag.lowercase(Locale.getDefault()).replace(" ", "-")
                if (category == "") {
                    category = "none"
                }
                if (category == "riba") {
                    category = "morski-sadeži"
                }
                if (TagList.tags.none { it.name == category }) {
                    category = "none"
                }
                tagListIds.add(getIdFromTagName(tag))
            }

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
                ),
                tags = tagListIds
            )

            val JSON = "application/json".toMediaType()
            val gson = GsonBuilder().create()
            val json = gson.toJson(restaurantDb)

            val body = json.toRequestBody(JSON)

            val request = Request.Builder()
                .url("http://localhost:3001/restaurants/${restaurant.id}")
                .put(body)
                .addHeader("Cookie", Auth.cookie)
                .build()

            val client = OkHttpClient()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("Unexpected code $response")
                val responseBody = response.body!!.string()
                println("Response body: " + responseBody)
                restaurant.isInDatabase = true
                restaurant.edited = false

                restaurant.id = DatabaseJsonToClass.getRestaurantId(responseBody)
                println("RESTAURANT ID: ${restaurant.id}")
            }
        } else {

            if (restaurant.isInDatabase) {
                println("Restaurant already exists in the database.")
                return
            }
            Auth.login("Sluzek", "1234")

            val tagList = getCategoriesFromMenus(restaurant)
            val tagListIds: MutableList<String> = mutableListOf()

            for (tag in tagList) {
                var category = tag.lowercase(Locale.getDefault()).replace(" ", "-")
                if (category == "") {
                    category = "none"
                }
                if (category == "riba") {
                    category = "morski-sadeži"
                }
                if (TagList.tags.none { it.name == category }) {
                    category = "none"
                }
                tagListIds.add(getIdFromTagName(category))
            }

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
                ),
                tags = tagListIds
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

            for (menu in restaurant.menuList) {
                pushMenuToDatabase(menu, restaurant.id)
            }
        }
    }

    fun pushMenuToDatabase(menu: Menu, restaurantId: String) {
        if (menu.isInDatabase && menu.edited) {
            Auth.login("Sluzek", "1234")

            var category = menu.category.lowercase(Locale.getDefault()).replace(" ", "-")
            if (category == "") {
                category = "none"
            }
            if (category == "riba") {
                category = "morski-sadeži"
            }
            if (TagList.tags.none { it.name == category }) {
                category = "none"
            }

            var tagId = getIdFromTagName(category)

            val menuDb = MenuDb(
                dish = menu.dish.replace("\n", ""),
                sideDishes = menu.extras,
                restaurantId = restaurantId,
                tag = tagId
            )

            val JSON = "application/json".toMediaType()
            val gson = GsonBuilder().create()
            val json = gson.toJson(menuDb)

            val body = json.toRequestBody(JSON)

            val request = Request.Builder()
                .url("http://localhost:3001/menus/${menu.id}")
                .put(body)
                .addHeader("Cookie", Auth.cookie)
                .build()

            val client = OkHttpClient()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("Unexpected code $response")
                val responseBody = response.body!!.string()
                println("Response body: " + responseBody)
                menu.isInDatabase = true
                menu.restaurantId = restaurantId

                menu.id = DatabaseJsonToClass.getRestaurantId(responseBody)
                menu.edited = false
                println("RESTAURANT ID: ${menu.id}")
            }

        } else {

            if (menu.isInDatabase) {
                println("Restaurant already exists in the database.")
                return
            }
            Auth.login("Sluzek", "1234")

            var category = menu.category.lowercase(Locale.getDefault()).replace(" ", "-")
            if (category == "") {
                category = "none"
            }
            if (category == "riba") {
                category = "morski-sadeži"
            }
            if (TagList.tags.none { it.name == category }) {
                category = "none"
            }

            var tagId = getIdFromTagName(category)

            val menuDb = MenuDb(
                dish = menu.dish.replace("\n", ""),
                sideDishes = menu.extras,
                restaurantId = restaurantId,
                tag = tagId
            )

            val JSON = "application/json".toMediaType()
            val gson = GsonBuilder().create()
            val json = gson.toJson(menuDb)

            val body = json.toRequestBody(JSON)

            val request = Request.Builder()
                .url("http://localhost:3001/menus")
                .post(body)
                //.addHeader("Authorization", "Bearer ${Auth.token}")
                .addHeader("Cookie", Auth.cookie)
                .build()

            val client = OkHttpClient()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("Unexpected code $response")
                val responseBody = response.body!!.string()
                println("Response body: " + responseBody)
                menu.isInDatabase = true
                menu.restaurantId = restaurantId

                menu.id = DatabaseJsonToClass.getRestaurantId(responseBody)
                println("RESTAURANT ID: ${menu.id}")
            }
        }
    }
}