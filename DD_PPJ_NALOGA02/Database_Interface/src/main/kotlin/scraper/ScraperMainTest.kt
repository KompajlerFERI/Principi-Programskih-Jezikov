package scraper

fun main() {
    val scraper = Scraper() // instanca razreda ko bo scrapal
    scraper.scrape() // funkcija ki pobere vse dol
    val restaurants = RestaurantList.restaurants // sem shrani
    restaurants.forEach() { restaurant ->
        println(restaurant)
    }

    // ene restavracije nimajo menija

}