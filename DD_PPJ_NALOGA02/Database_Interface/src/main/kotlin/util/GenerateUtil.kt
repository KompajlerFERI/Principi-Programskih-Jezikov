package util

import scraper.Menu
import scraper.Restaurant
import kotlin.random.Random
import io.github.serpro69.kfaker.Faker

object GenerateUtil {
    private val faker = Faker()

    fun generateRandomRestaurants(count: Int, menuPerRestaurant: Int, minPrice: Float, maxPrice: Float, latitude: Double, longitude: Double, radius: Double): List<Restaurant> {
        val randomRestaurants = mutableListOf<Restaurant>()
        for (i in 1..count) {
            val lat = latitude + Random.nextDouble(-radius, radius)
            val lon = longitude + Random.nextDouble(-radius, radius)

            val restaurant = Restaurant(
                name = faker.company.name(),
                address = faker.address.streetAddress(),
                payPrice = String.format("%.2f", Random.nextFloat() * (maxPrice - minPrice) + minPrice),
                fullPrice = String.format("%.2f", (Random.nextFloat() * (maxPrice - minPrice) + minPrice) * 2),
                phoneNumber = generateRandomSlovenianPhoneNumber(),
                workingTimes = generateRandomWorkingTimes(),
                latitude = lat.toString(),
                longitude = lon.toString(),
                menuList = generateRandomMenus(menuPerRestaurant).toMutableList(),
                scrapped = false,
                isInDatabase = false
            )
            randomRestaurants.add(restaurant)
        }
        return randomRestaurants
    }

    fun generateRandomWorkingTimes(): MutableList<String> {
        val daysOfWeek = listOf("Ponedeljek", "Torek", "Sreda", "Četrtek", "Petek", "Sobota", "Nedelja")
        val workingTimes = mutableListOf<String>()

        for (day in daysOfWeek) {
            val startTime = generateRandomTime()
            val endTime = generateRandomTime()
            val workingTime = "$day : $startTime - $endTime"
            workingTimes.add(workingTime)
        }

        return workingTimes
    }

    fun generateRandomTime(): String {
        val hour = Random.nextInt(0, 24)
        val minute = if (Random.nextBoolean()) 0 else 30
        return String.format("%02d:%02d", hour, minute)
    }

    fun generateRandomSlovenianPhoneNumber(): String {
        val prefixes = listOf("030", "040", "068", "069", "031", "041", "051", "065", "070", "071", "064", "059", "081", "082", "083", "080", "089", "090")
        val prefix = prefixes.random()
        val number = StringBuilder(prefix)
        repeat(6) {
            if (it == 0 || it == 3) number.append(" ")
            number.append(Random.nextInt(0, 10))
        }
        return number.toString()
    }

    fun generateRandomMenus(count: Int): List<Menu> {
        val randomMenus = mutableListOf<Menu>()
        repeat(count) {
            val menu = Menu(
                dish = faker.food.dish(),
                extras = mutableListOf(faker.food.fruits()),
                category = faker.food.spices()
            )
            randomMenus.add(menu)
        }
        return randomMenus
    }
}