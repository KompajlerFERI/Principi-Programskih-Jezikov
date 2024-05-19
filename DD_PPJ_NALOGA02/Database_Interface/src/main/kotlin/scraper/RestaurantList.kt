package scraper

object RestaurantList {
    val restaurants = mutableListOf<Restaurant>()

    fun add(restaurant: Restaurant) =
        restaurants.add(restaurant)
}