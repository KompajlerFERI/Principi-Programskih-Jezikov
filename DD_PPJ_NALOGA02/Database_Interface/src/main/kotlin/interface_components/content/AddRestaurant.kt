package interface_components.content

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import interface_components.textColor
import scraper.Restaurant
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddRestaurant(restaurants: MutableState<List<Restaurant>>) {
    var restaurantName by remember { mutableStateOf("") }
    var menuCount by remember { mutableStateOf("1") }
    var menuItems: List<String> by remember { mutableStateOf(List(menuCount.toInt()) { "" }) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Restaurant",
            style = TextStyle(fontSize = 22.sp),
            color = textColor
        )

        TextField(
            value = restaurantName,
            onValueChange = { restaurantName = it },
            label = { Text("Restaurant Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = menuCount,
            onValueChange = {
                menuCount = it
                menuItems = List(it.toInt()) { "" }
            },
            label = { Text("Number of Menus") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // TextFields for menu items
        for (index in menuItems.indices) {
            TextField(
                value = menuItems[index],
                onValueChange = { newValue ->
                    menuItems = menuItems.toMutableList().apply { set(index, newValue) }
                },
                label = { Text("Menu Item ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(onClick = {
            val restaurant = Restaurant(restaurantName, menuItems.joinToString(", "))
            restaurants.value = restaurants.value + restaurant
        }) {
            Text("Add Restaurant")
        }
    }
}
