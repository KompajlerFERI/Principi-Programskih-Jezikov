package interface_components.content

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
fun ShowRestaurants(state: LazyListState, restaurants: MutableState<List<Restaurant>>, refresh: MutableState<Boolean>) {
    if (refresh.value || !refresh.value) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state
        ) {
            items(restaurants.value) { restaurant ->
                RestaurantItem(
                    refresh = refresh,
                    restaurant = restaurant,
                    onEditClick = { editedRestaurant ->
                        // Find and update the edited restaurant
                        val index = restaurants.value.indexOf(restaurant)
                        if (index != -1) {
                            val updatedList = restaurants.value.toMutableList()
                            updatedList[index] = editedRestaurant
                            restaurants.value = updatedList
                            refresh.value = !refresh.value
                        }
                    },
                    onSaveClick = {
                        // TODO: Save the restaurant to the database
                    },
                    onDeleteClick = {
                        restaurants.value = restaurants.value.toMutableList().apply { remove(restaurant) }
                        refresh.value = !refresh.value
                    }
                )
            }
        }
    }
}