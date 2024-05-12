package scraping

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.attribute
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.article
import it.skrape.selects.html5.div
import it.skrape.selects.html5.h3

fun main() {
    println("========== scraping - printing example ==========")
    // docs: https://docs.skrape.it/docs/
    skrape(HttpFetcher) {   // <-- pass any Fetcher, e.g. HttpFetcher, BrowserFetcher, ...
        request {
            // request options goes here, e.g the most basic would be url
            // https://docs.skrape.it/docs/http-client/request-options
            url = "https://www.studentska-prehrana.si/sl/restaurant"
        }

        response {
            // do stuff with the response like parsing the response body
            // https://docs.skrape.it/docs/http-client/response
            println("http status code: ${status { code }}")
            println("http status message: ${status { message }}")

            htmlDocument {
                div {
                    withClass = "row.restaurant-row"

                    findAll {
                        forEach {
                            val city = it.attributes["data-city"]
                            if (city == "MARIBOR") {
                                val restaurantName = it.attributes["data-lokal"]
                                //val restaurantLink = it.a { findFirst( attribute("href")) }
                                println("Restaurant Name: $restaurantName")
                                //println("Restaurant Link: https://www.studentska-prehrana.si$restaurantLink")
                            }
                        }
                    }
                }
            }
        }
    }
}