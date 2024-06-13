package util

import com.google.gson.JsonParser
import scraper.*

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

        val existingRestaurant = RestaurantList.restaurants.find { it.id == restaurant.id }

        if (existingRestaurant == null) {
            // If the restaurant does not exist in the list, add it
            RestaurantList.add(restaurant)
        } else {
            println("Restaurant exists already!")
        }
    }

    fun JsonTagToClass(JsonString: String) {
        val jsonObject = JsonParser.parseString(JsonString).asJsonObject
        val fieldNames = jsonObject.keySet()

        var tag = Tag()
        fieldNames.forEach { fieldName ->
            val fieldValue = jsonObject.get(fieldName)

            if (fieldName == "name") {
                tag.name = fieldValue.asString
            }
            if (fieldName == "_id") {
                tag.id = fieldValue.asString
            }

        }
        tag.isInDatabase = true
//        if (!TagList.tags.any { it.name == tag.name }) {
//            TagList.add(tag)
//        } else {
//            println("Tag already exists in the list.")
//        }

        val existingTag = TagList.tags.find { it.id == tag.id }

        if (existingTag == null) {
            // If the restaurant does not exist in the list, add it
            TagList.add(tag)
        } else {
            println("Tag already exists in the list.")
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
            if (fieldName == "_id") {
                menuItem.id = fieldValue.asString
            }
            if (fieldName == "restaurant") {
                val restaurantId = fieldValue.asString

                RestaurantList.restaurants.forEach { restaurant ->
                    val existingMenu = restaurant.menuList.find{ it.id == menuItem.id }
                    if (restaurant.id == restaurantId && !restaurant.menuList.any { it.dish == menuItem.dish }) {
                        if (existingMenu == null) {
                            menuItem.isInDatabase = true
                            restaurant.menuList.add(menuItem)
                        }
                        else {
                            println("Menu item already exists in the restaurant.")
                        }
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

    fun JsonToUser(JsonString: String) {
        val jsonObject = JsonParser.parseString(JsonString).asJsonObject
        val fieldNames = jsonObject.keySet()

        var user = User()

        fieldNames.forEach { fieldName ->
            val fieldValue = jsonObject.get(fieldName)

            if (fieldName == "username") {
                val username = fieldValue.asString
                user.username = username
            }
            if (fieldName == "firstName") {
                val firstName = fieldValue.asString
                user.firstName = firstName
            }
            if (fieldName == "lastName") {
                val lastName = fieldValue.asString
                user.lastName = lastName
            }
            if (fieldName == "email") {
                val email = fieldValue.asString
                user.email = email
            }
            if (fieldName == "userType") {
                val userType = fieldValue.asString
                user.userType = userType
            }
            if (fieldName == "_id") {
                val id = fieldValue.asString
                user.id = id
            }
        }

        if (user.userType != "admin") {
            if (!UserList.users.any { it.id == user.id }) {
                UserList.addUser(user)
            } else {
                println("User already exists in the list.")
            }
        }
    }
}