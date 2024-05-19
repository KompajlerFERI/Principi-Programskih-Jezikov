package scraper

fun main() {
    val scraper = Scraper()
    scraper.scrape()
    val restaurants = RestaurantList.restaurants
    restaurants.forEach() { restaurant ->
        println(restaurant)
    }
}