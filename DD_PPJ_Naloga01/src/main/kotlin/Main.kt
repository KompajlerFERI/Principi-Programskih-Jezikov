package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.attribute
import it.skrape.selects.eachText
import it.skrape.selects.html5.*

fun main() {
    println("========== Scraping restaurants - printing ==========")
    val restaurantLinks = mutableListOf<Pair<String?, String>>()

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
                                println("Restaurant Name: $restaurantName")
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
        println("Restaurant Name: ${pair.first}")
        try {
            skrape(HttpFetcher) {
                request {
                    url = pair.second
                    timeout = 10000
                    //url = "https://www.studentska-prehrana.si/sl/restaurant/Details/3237"
                }

                response {
                    htmlDocument {
                        div {
                            withId = "menu-list"
                            findFirst {
                                div {
                                    withClass = "shadow-wrapper"
                                    findAll {
                                        h5 {
                                            findAll {
                                                val menus = eachText
                                                menus.forEach { menu ->
                                                    println("Menu: $menu")
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
        } catch (e: it.skrape.selects.ElementNotFoundException) {
            println("MENU UNAVAILABLE")
        }
    }
}

//    skrape(HttpFetcher) {
//        request {
//            //url = pair.second
//            url = "https://www.studentska-prehrana.si/sl/restaurant/Details/3237"
//        }
//
//        response {
//            htmlDocument {
//                div {
//                    withId = "menu-list"
//                    findFirst {
//                        div {
//                            withClass = "shadow-wrapper"
//                            findAll {
//                                h5 {
//                                    findAll {
//                                        val menus = eachText
//                                        menus.forEach { menu ->
//                                            println("Menu: $menu")
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//}