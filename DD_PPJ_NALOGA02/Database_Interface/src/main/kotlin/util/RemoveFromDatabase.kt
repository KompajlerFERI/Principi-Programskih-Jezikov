package util

import okhttp3.OkHttpClient
import okhttp3.Request
import scraper.Menu
import scraper.Restaurant
import scraper.RestaurantList

object RemoveFromDatabase {
    fun removeRestaurant(restaurant: Restaurant) {
        if (!restaurant.isInDatabase) {
            println("Restaurant is not in the database.")
            return
        }
        Auth.login("Sluzek", "1234")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://localhost:3001/restaurants/${restaurant.id}")
            .delete()
            .addHeader("Cookie", Auth.cookie)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")

            println("Response body: " + response.body!!.string())
        }
        if (RestaurantList.restaurants.contains(restaurant)) {
            RestaurantList.restaurants.remove(restaurant)
        }
        restaurant.deleted = true

//        for (menu in restaurant.menuList) {
//            removeMenu(menu)
//        }
    }

    fun removeMenu(menu: Menu) {
        if (!menu.isInDatabase) {
            println("Menu is not in the database.")
            return
        }
        Auth.login("Sluzek", "1234")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://localhost:3001/menus/${menu.id}")
            .delete()
            .addHeader("Cookie", Auth.cookie)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")

            println("Response body: " + response.body!!.string())
            println("MENU: ${menu.dish} removed from the database.")
        }
    }
}