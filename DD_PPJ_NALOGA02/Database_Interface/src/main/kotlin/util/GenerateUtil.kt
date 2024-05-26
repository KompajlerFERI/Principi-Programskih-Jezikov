package util

import scraper.Menu
import scraper.Restaurant
import kotlin.random.Random

object GenerateUtil {
    fun generateRandomRestaurants(count: Int, menuPerRestaurant: Int, minPrice: Float, maxPrice: Float, latitude: Double, longitude: Double, radius: Double): List<Restaurant> {
        val randomRestaurants = mutableListOf<Restaurant>()
        for (i in 1..count) {
            val lat = latitude + Random.nextDouble(-radius, radius)
            val lon = longitude + Random.nextDouble(-radius, radius)
            val restaurant = Restaurant(
                name = "Restaurant ${Random.nextInt(100, 999)}",
                address = "Address ${Random.nextInt(1, 100)}",
                payPrice = String.format("%.2f", Random.nextFloat() * (maxPrice - minPrice) + minPrice),
                phoneNumber = "0${Random.nextInt(30, 49)} ${Random.nextInt(100, 999)} ${Random.nextInt(100, 999)}",
                workingTimes = mutableListOf("9:00 AM - 5:00 PM"),
                latitude = lat.toString(),
                longitude = lon.toString(),
                menuList = generateRandomMenus(menuPerRestaurant).toMutableList()
            )
            randomRestaurants.add(restaurant)
        }
        return randomRestaurants
    }

    fun generateRandomMenus(count: Int): List<Menu> {
        val randomMenus = mutableListOf<Menu>()
        for (i in 1..count) {
            val menu = Menu(
                dish = "Dish ${Random.nextInt(1, 100)}",
                extras = mutableListOf("Extra ${Random.nextInt(1, 10)}"),
                category = "Category ${Random.nextInt(1, 5)}"
            )
            randomMenus.add(menu)
        }
        return randomMenus
    }
}