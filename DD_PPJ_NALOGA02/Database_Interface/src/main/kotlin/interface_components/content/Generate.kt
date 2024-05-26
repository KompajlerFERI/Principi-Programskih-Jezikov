package interface_components.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.textColor
import scraper.Restaurant
import scraper.Menu
import util.GenerateUtil
import kotlin.random.Random

@Composable
fun Generate(restaurants: MutableState<List<Restaurant>>) {
    var restaurantCount by remember { mutableStateOf("1") }
    var menuCount by remember { mutableStateOf("5") }
    var minPrice by remember { mutableStateOf("2.00") }
    var maxPrice by remember { mutableStateOf("20.00") }
    var latitude by remember { mutableStateOf("0.0") }
    var longitude by remember { mutableStateOf("0.0") }
    var radius by remember { mutableStateOf("10.0") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .width(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Generate Random Restaurants",
                style = TextStyle(fontSize = 22.sp),
                color = textColor
            )

            TextField(
                value = restaurantCount,
                onValueChange = { restaurantCount = it },
                label = { Text("Number of Restaurants") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = menuCount,
                onValueChange = { menuCount = it },
                label = { Text("Number of Menus per Restaurant") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = minPrice,
                    onValueChange = { minPrice = it },
                    label = { Text("Minimum Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                TextField(
                    value = maxPrice,
                    onValueChange = { maxPrice = it },
                    label = { Text("Maximum Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = { Text("Latitude") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                TextField(
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = { Text("Longitude") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            TextField(
                value = radius,
                onValueChange = { radius = it },
                label = { Text("Radius") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                val count = restaurantCount.toIntOrNull() ?: 1
                val menuPerRestaurant = menuCount.toIntOrNull() ?: 5
                val min = minPrice.toFloatOrNull() ?: 2.00f
                val max = maxPrice.toFloatOrNull() ?: 20.00f
                val newRestaurants = GenerateUtil.generateRandomRestaurants(count, menuPerRestaurant, min, max, latitude.toDouble(), longitude.toDouble(), radius.toDouble())
                restaurants.value = restaurants.value + newRestaurants
            }) {
                Text("Generate")
            }
        }
    }
}
