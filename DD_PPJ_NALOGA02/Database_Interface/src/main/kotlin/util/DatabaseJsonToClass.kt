package util

import com.google.gson.JsonParser
import scraper.Menu
import scraper.Restaurant
import scraper.RestaurantList

object DatabaseJsonToClass {
    fun JsonToRestaurantClass(JsonString: String) {
        val jsonObject = JsonParser.parseString(JsonString).asJsonObject
        val fieldNames = jsonObject.keySet()

        var restaurant = Restaurant()

        fieldNames.forEach { fieldName ->
            val fieldValue = jsonObject.get(fieldName)

            if (fieldName == "name") {
                restaurant.name = fieldValue.asString
            }
            if (fieldName == "owner") {
                restaurant.ownerId = fieldValue.asString
            }
            if (fieldName == "mealPrice") {
                restaurant.fullPrice = fieldValue.asString
            }
            if (fieldName == "mealSurcharge") {
                restaurant.payPrice = fieldValue.asString
            }
            if (fieldName == "location") {
                val locationObject = fieldValue.asJsonObject
                val coordinatesArray = locationObject.get("coordinates").asJsonArray
                val longitude = coordinatesArray.get(0).asDouble
                val latitude = coordinatesArray.get(1).asDouble
                restaurant.longitude = longitude.toString()
                restaurant.latitude = latitude.toString()
            }
            if (fieldName == "address") {
                restaurant.address = fieldValue.asString
            }
            if (fieldName == "workingHours" && fieldValue.isJsonArray) {
                val workingHoursArray = fieldValue.asJsonArray
                workingHoursArray.forEach { workingHourElement ->
                    val workingHourObject = workingHourElement.asJsonObject
                    val day = workingHourObject.get("day").asString
                    val open = workingHourObject.get("open").asString
                    val close = workingHourObject.get("close").asString

                    if (open == "Zaprto" || close == "Zaprto") {
                        restaurant.workingTimes.add("$day : Zaprto")
                    }
                    else {
                        restaurant.workingTimes.add("$day : $open - $close")
                    }
                }
            }
            if (fieldName == "_id") {
                restaurant.id = fieldValue.asString
            }
        }
        restaurant.isInDatabase = true
        restaurant.scrapped = false
        restaurant.edited = false

        if (!RestaurantList.restaurants.any { it.name == restaurant.name }) {
            RestaurantList.add(restaurant)
        } else {
            println("Restaurant already exists in the list.")
        }
    }
    fun JsonToMenuItem(JsonString: String) {
        val jsonObject = JsonParser.parseString(JsonString).asJsonObject
        val fieldNames = jsonObject.keySet()

        var menuItem = Menu()

        fieldNames.forEach { fieldName ->
            val fieldValue = jsonObject.get(fieldName)

            if (fieldName == "dish") {
                val dish = fieldValue.asString
                menuItem.dish = dish
            }
            if (fieldName == "sideDishes") {
                val sideDishesArray = fieldValue.asJsonArray
                sideDishesArray.forEach { sideDishElement ->
                    val sideDish = sideDishElement.asString
                    menuItem.extras.add(sideDish)
                }
            }
            if (fieldName == "tag") {
                val tagObject = fieldValue.asJsonObject
                //val tagId = tagObject.get("_id").asString
                val tagName = tagObject.get("name").asString
                menuItem.category = tagName
            }
            if (fieldName == "restaurant") {
                val restaurantId = fieldValue.asString
                RestaurantList.restaurants.forEach { restaurant ->
                    if (restaurant.id == restaurantId && !restaurant.menuList.any { it.dish == menuItem.dish }) {
                        restaurant.menuList.add(menuItem)
                    }
                }
            }
        }
    }

    fun getRestaurantId(JsonString: String): String {
        val jsonObject = JsonParser.parseString(JsonString).asJsonObject
        val fieldNames = jsonObject.keySet()
        var restaurantId = ""

        fieldNames.forEach { fieldName ->
            val fieldValue = jsonObject.get(fieldName)

            if (fieldName == "_id") {
                restaurantId = fieldValue.asString
            }
        }
        return restaurantId
    }
}