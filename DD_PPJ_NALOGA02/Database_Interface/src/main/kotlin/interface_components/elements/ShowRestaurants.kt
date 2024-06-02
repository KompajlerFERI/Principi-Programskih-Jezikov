package interface_components.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import scraper.Restaurant

@Composable
fun ShowRestaurants(
    state: LazyListState,
    restaurants: MutableList<Restaurant>,
    refresh: MutableState<Boolean>,
    showScrappedOnly: Boolean = false
) {
    val displayedRestaurants = if (showScrappedOnly) {
        restaurants.filter { it.scrapped }
    } else {
        restaurants
    }

    if (refresh.value || !refresh.value) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state
        ) {
            items(displayedRestaurants)  { restaurant ->
                RestaurantItem(
                    refresh = refresh,
                    restaurant = restaurant,
                    onEditClick = { editedRestaurant ->
                        // Find and update the edited restaurant
                        val index = restaurants.indexOf(restaurant)
                        if (index != -1) {
                            restaurants[index] = editedRestaurant

                            // TODO: Edit the restaurant in the database

                            refresh.value = !refresh.value
                        }
                    },
                    onDeleteClick = {
                        restaurants.remove(restaurant)

                        // TODO: Delete the restaurant in the database

                        refresh.value = !refresh.value
                    }
                )
            }
        }
    }
}