package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.attribute
import it.skrape.selects.eachText
import it.skrape.selects.html5.*
import org.w3c.dom.Text

class Restaurant(val name: String, val fullPrice: String, val payPrice: String, val longitude: String, val latitude: String, val address: String, val link: String) {
    override fun toString(): String {
        return "Restaurant Name: $name\n" +
                "Restaurant full price: $fullPrice€\n" +
                "Restaurant pay price: $payPrice€\n" +
                "Restaurant longitude: $longitude\n" +
                "Restaurant latitude: $latitude\n" +
                "Restaurant address: $address\n" +
                "Restaurant Link: $link\n"
    }

}

class Menu(val menu: String, val extras: List<String>) {
    override fun toString(): String {
        return "Menu: $menu\n" +
                "Extras: $extras\n"
    }
}

fun isWholeNumber(num: Double): Boolean {
    if (num != Math.floor(num)) {
        throw IllegalArgumentException("$num is not a whole number.")
    }
    return true
}

fun getPhoneNumberFromString(input: String): String {
    val matchResult = Regex("""\((.*?)\)""").find(input)
    val phoneNumber = matchResult?.groups?.get(1)?.value
    return if (phoneNumber.isNullOrEmpty()) "NO PHONE NUMBER" else phoneNumber
}

fun getTimeFromString(input: String): String {
    val regex = """[0-9]+:[0-9]+ - [0-9]+:[0-9]+""".toRegex()
    return regex.find(input)?.value.toString()
}

fun getWorkingTimesFromString(input: String): List<String> {
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



fun main() {
    println("========== Scraping restaurants - printing ==========")
    val restaurantLinks = mutableListOf<Pair<String?, String>>()
    val menusList = mutableListOf<String>()
    val menuCategory = mutableListOf<String>()
    var num = 3
    var index = 0

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
                                println("Restaurant Name: $restaurantName")
                                println("Restaurant full price: $restaurantFullPrice€")
                                println("Restaurant pay price: $restaurantPayPrice€")
                                println("Restaurant longitude: $restaurantLongitude")
                                println("Restaurant latitude: $restaurantLatitude")
                                println("Restaurant address: $restaurantAddress")
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
                                println("Restaurant Link: " + redirectLink)
                                restaurantLinks.add(Pair(restaurantName, redirectLink))
                            }
                        }
                    }
                }
            }
        }
    }

    println("========== Scraping menus - printing ==========")
    restaurantLinks.forEach { pair ->
        println("_________________________________________________\nRestaurant Name: ${pair.first}")
        try {
            skrape(HttpFetcher) {
                request {
                    url = pair.second
                    timeout = 10000
                    //url = "https://www.studentska-prehrana.si/sl/restaurant/Details/3237"
                }

                response {
                    htmlDocument {
                        println("\n")
                        div {
                            withClass = "col-md-12.margin-top-20"
                            findFirst {
                                div {
                                    withClass = "col-md-12.text-bold"
                                    findFirst {
                                        val content = text
                                        getWorkingTimesFromString(content).forEach { workingTime ->
                                            println(workingTime)
                                        }
                                    }
                                }
                            }
                        }
                        println("\n")
                        div {
                            withId = "menu-list"
                            findFirst {
                                div {
                                    withClass = "shadow-wrapper"
                                    findAll {
                                        div {
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

                                        div {
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

                                                ul {
                                                    withClass = "list-unstyled"
                                                    findAll {
                                                        li {
                                                            findAll {
                                                                val extras = eachText
                                                                println((extras.size.toDouble() / 3))
                                                                isWholeNumber((extras.size.toDouble() / 3))
                                                                extras.forEach { extra ->
                                                                    if (num == 3) {
                                                                        println("\n" + menusList[index] + "|" + menuCategory[index])
                                                                        index++
                                                                        num = 0
                                                                    }
                                                                    num++
                                                                    if (extra.isNotEmpty()) {
                                                                        println(extra)
                                                                    }
                                                                }
                                                            }
                                                        }
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
                                        println("Phone number: $phoneNumber")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: it.skrape.selects.ElementNotFoundException) {
            println("MENU UNAVAILABLE")
        } catch (e: Exception) {
            println("ERROR: ${e.message}")
        }
    }
}

