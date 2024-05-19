package scraper

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.attribute
import it.skrape.selects.eachText
import it.skrape.selects.html5.*

class Scraper {
    private fun isWholeNumber(num: Double): Boolean {
        if (num != Math.floor(num)) {
            throw IllegalArgumentException("$num is not a whole number.")
        }
        return true
    }

    private fun getPhoneNumberFromString(input: String): String {
        val matchResult = Regex("""\((.*?)\)""").find(input)
        val phoneNumber = matchResult?.groups?.get(1)?.value
        return if (phoneNumber.isNullOrEmpty()) "NO PHONE NUMBER" else phoneNumber
    }

    private fun getTimeFromString(input: String): String {
        val regex = """[0-9]+:[0-9]+ - [0-9]+:[0-9]+""".toRegex()
        return regex.find(input)?.value.toString()
    }

    private fun getWorkingTimesFromString(input: String): List<String> {
        val regex = """([a-zA-ZčšžČŠŽ]+ : [0-9]+:[0-9]+ - [0-9]+:[0-9]+|[a-zA-ZčšžČŠŽ]+ : [a-zA-Z]+)""".toRegex()
        val matches = regex.findAll(input)
        val workingTimes = mutableListOf<String>()

        val firstMatch = matches.first()
        val firstWord = firstMatch.value.split(" ")[0]
        val workingHours = getTimeFromString(firstMatch.value)
        if (firstWord == "tednom") {
            workingTimes.add("Ponedeljek : $workingHours")
            workingTimes.add("Torek : $workingHours")
            workingTimes.add("Sreda : $workingHours")
            workingTimes.add("Četrtek : $workingHours")
            workingTimes.add("Petek : $workingHours")

            workingTimes.add(matches.elementAt(1).value)
            workingTimes.add(matches.elementAt(2).value)
        }
        else {
            matches.forEach { matchResult ->
                workingTimes.add(matchResult.value)
            }
        }
        return workingTimes
    }

    fun scrape() {
        println("========== Scraping restaurants - printing ==========")
        val restaurantLinks = mutableListOf<Pair<String?, String>>()
        val menusList = mutableListOf<String>()
        val menuCategory = mutableListOf<String>()
        var num = 3
        var index = 0
        var listCounter = 0

        var fullDishInfoString : String = ""
        var fullDishInfo = mutableListOf<String>()

        //var restaurantList = RestaurantList


        skrape(HttpFetcher) {
            request {
                url = "https://www.studentska-prehrana.si/sl/restaurant"
            }

            response {
                htmlDocument {
                    div {
                        withClass = "row.restaurant-row"

                        findAll {
                            forEach {
                                val city = it.attributes["data-city"]
                                if (city == "MARIBOR") {
                                    val restaurantName = it.attributes["data-lokal"]
                                    val restaurantFullPrice = it.attributes["data-cena"]
                                    val restaurantPayPrice = it.attributes["data-doplacilo"]
                                    val restaurantLongitude = it.attributes["data-lon"]
                                    val restaurantLatitude = it.attributes["data-lat"]
                                    val restaurantAddress = it.attributes["data-naslov"]
                                    //println("Restaurant Name: $restaurantName")
                                    //println("Restaurant full price: $restaurantFullPrice€")
                                    //println("Restaurant pay price: $restaurantPayPrice€")
                                    //println("Restaurant longitude: $restaurantLongitude")
                                    //println("Restaurant latitude: $restaurantLatitude")
                                    //println("Restaurant address: $restaurantAddress")
                                    val restaurantLink = it.h2 {
                                        withClass = "no-margin.color-blue"
                                        findFirst {
                                            a {
                                                findFirst {
                                                    attribute("href")
                                                }
                                            }
                                        }
                                    }
                                    val redirectLink = "https://www.studentska-prehrana.si" + restaurantLink
                                    //println("Restaurant Link: " + redirectLink)
                                    restaurantLinks.add(Pair(restaurantName, redirectLink))
                                    RestaurantList.add(Restaurant(name = restaurantName!!, fullPrice = restaurantFullPrice!!, payPrice = restaurantPayPrice!!, longitude = restaurantLongitude!!, latitude = restaurantLatitude!!, address = restaurantAddress!!))
                                }
                            }
                        }
                    }
                }
            }
        }

        println("========== Scraping menus - printing ==========")
        for (pair in restaurantLinks) {
//        if (pair.first == "CITY GRILL" || pair.first == "CITY GRILL - DOSTAVA") {
//            println("CITY GRILL SPOTTED!!!!!!!!!!!!!!!")
//            continue
//        }
            //println("_________________________________________________\nRestaurant Name: ${pair.first}")
            try {
                skrape(HttpFetcher) {
                    request {
                        url = pair.second
                        timeout = 10000
                        //url = "https://www.studentska-prehrana.si/sl/restaurant/Details/3237"
                    }

                    response {
                        htmlDocument {
                            //println("\n")
                            div {
                                withClass = "col-md-12.margin-top-20"
                                findFirst {
                                    div {
                                        withClass = "col-md-12.text-bold"
                                        findFirst {
                                            val content = text
                                            getWorkingTimesFromString(content).forEach { workingTime ->
                                                //println(workingTime)

                                                RestaurantList.restaurants.forEach { restaurant ->
                                                    if (restaurant.name == pair.first) {
                                                        restaurant.workingTimes.add(workingTime)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //println("\n")
                            div {
                                withId = "menu-list"
                                findFirst {
                                    div {
                                        withClass = "shadow-wrapper"
                                        findAll {
                                            div { // here it finds menu category
                                                withClass = "col-md-3.pull-right"
                                                findAll {
                                                    img {
                                                        withClass = "pull-right"
                                                        findAll {
                                                            val foodCategorys = attribute("title")
                                                            //println("FOOD CATEGORY: $foodCategorys")
                                                            val foodCategorysSplit = foodCategorys.split(",")
                                                            foodCategorysSplit.forEach { category ->
                                                                val trimmedCategory = category.trim()
                                                                menuCategory.add(trimmedCategory)
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            div {  //here it finds dish title
                                                withClass = "col.col-md-8.margin-left-10"
                                                findAll {
                                                    h5 {
                                                        findAll {
                                                            val menus = eachText
                                                            menus.forEach { menu ->
                                                                //println("Menu: $menu")
                                                                menusList.add(menu)
                                                            }
                                                        }
                                                    }

                                                    ul {  //here it finds extras
                                                        withClass = "list-unstyled"
                                                        findAll {
                                                            li {
                                                                findAll {
                                                                    val extras = eachText
                                                                    //println((extras.size.toDouble() / 3))
                                                                    isWholeNumber((extras.size.toDouble() / 3))
                                                                    extras.forEach { extra ->
                                                                        if (num == 3) {
                                                                            if (listCounter == 3) {
                                                                                if (fullDishInfoString.isNotEmpty()) {
                                                                                    fullDishInfo.add(fullDishInfoString)
                                                                                }
                                                                                fullDishInfoString = ""
                                                                                listCounter = 0
                                                                            }
                                                                            //println("\n" + menusList[index] + ";" + menuCategory[index])
                                                                            if (menusList.size != menuCategory.size) {
                                                                                fullDishInfoString += "\n" + menusList[index] + ";CATEGORY UNAVAILABLE;"
                                                                                index++
                                                                                num = 0
                                                                            }
                                                                            else {
                                                                                fullDishInfoString += "\n" + menusList[index] + ";" + menuCategory[index] + ";"
                                                                                index++
                                                                                num = 0
                                                                            }
                                                                        }
                                                                        num++

                                                                        //println(extra)
                                                                        fullDishInfoString += "%%%" + extra
                                                                        listCounter++
                                                                    }
                                                                    if (fullDishInfoString.isNotEmpty()) {
                                                                        fullDishInfo.add(fullDishInfoString)
                                                                        fullDishInfoString = ""
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }


                                                    RestaurantList.restaurants.forEach { restaurant ->
                                                        if (restaurant.name == pair.first) {
                                                            restaurant.menuListString = fullDishInfo.toMutableList()
                                                            fullDishInfo.clear()
                                                            index = 0
                                                            menusList.clear()
                                                            menuCategory.clear()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            div {
                                withClass = "col-md-6"
                                findFirst {
                                    small {
                                        findFirst {
                                            var phoneNumber = text
                                            phoneNumber = getPhoneNumberFromString(phoneNumber)
                                            //println("Phone number: $phoneNumber")
                                            RestaurantList.restaurants.forEach { restaurant ->
                                                if (restaurant.name == pair.first) {
                                                    restaurant.phoneNumber = phoneNumber
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                restaurantList.restaurants.forEach { restaurant ->
//                    if (restaurant.name == pair.first) {
//                        print("=================================================\n")
//                        print(restaurant.toString())
//                    }
//                }
                }
            } catch (e: it.skrape.selects.ElementNotFoundException) {
                //println("MENU UNAVAILABLE")
                RestaurantList.restaurants.forEach { restaurant ->
                    if (restaurant.name == pair.first) {
                        //restaurant.menuList.add(Menu("MENU UNAVAILABLE"))
                        restaurant.menuListString.add("MENU UNAVAILABLE")
                    }
                }
            } catch (e: Exception) {
                println("ERROR: ${e.message}")
            }
            println("SCRAPED: ${pair.first}")
        }

        RestaurantList.restaurants.forEach() { restaurant ->
            restaurant.menuListString.forEach { menuString ->
                if (menuString == "MENU UNAVAILABLE") {
                    restaurant.menuList.add(Menu("MENU UNAVAILABLE"))
                } else {
                    restaurant.menuList.add(Menu().menuStringToMenu(menuString))
                }
            }
        }
    }
}