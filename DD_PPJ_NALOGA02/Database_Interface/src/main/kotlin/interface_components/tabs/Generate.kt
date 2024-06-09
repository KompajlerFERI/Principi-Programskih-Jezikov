package interface_components.tabs

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.elementGradientColorDarker
import interface_components.elementGradientColorLighter
import interface_components.textColor
import interface_components.textColorDark
import scraper.Restaurant
import util.GenerateUtil
import util.ValidityUtil

@Composable
fun Generate(restaurants: MutableList<Restaurant>) {
    var restaurantCount by remember { mutableStateOf("1") }
    var menuCount by remember { mutableStateOf("5") }
    var minPrice by remember { mutableStateOf("2.00") }
    var maxPrice by remember { mutableStateOf("20.00") }
    var latitude by remember { mutableStateOf("0.0") }
    var longitude by remember { mutableStateOf("0.0") }
    var radius by remember { mutableStateOf("10.0") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.6f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            state = state
        ) {
            item {
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
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )

                    TextField(
                        value = menuCount,
                        onValueChange = { menuCount = it },
                        label = { Text("Number of Menus per Restaurant") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
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
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(color = textColor),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = textColor.copy(alpha = 0.3f),
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                            )
                        )

                        TextField(
                            value = maxPrice,
                            onValueChange = { maxPrice = it },
                            label = { Text("Maximum Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(color = textColor),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = textColor.copy(alpha = 0.3f),
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                            )
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
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(color = textColor),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = textColor.copy(alpha = 0.3f),
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                            )
                        )

                        TextField(
                            value = longitude,
                            onValueChange = { longitude = it },
                            label = { Text("Longitude") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(color = textColor),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = textColor.copy(alpha = 0.3f),
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                            )
                        )
                    }

                    TextField(
                        value = radius,
                        onValueChange = { radius = it },
                        label = { Text("Radius") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )

                    Button(
                        onClick = {
                            val count = restaurantCount.toIntOrNull() ?: 1
                            val menuPerRestaurant = menuCount.toIntOrNull() ?: 5
                            val min = minPrice.toFloatOrNull() ?: 2.00f
                            val max = maxPrice.toFloatOrNull() ?: 20.00f
                            val newRestaurants = GenerateUtil.generateRandomRestaurants(
                                count,
                                menuPerRestaurant,
                                min,
                                max,
                                latitude.toDouble(),
                                longitude.toDouble(),
                                radius.toDouble()
                            )
                            for (newRestaurant in newRestaurants) {

                                restaurants.add(newRestaurant)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, textColor, RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(elementGradientColorLighter, elementGradientColorDarker),
                                        center = Offset(40f, 40f),
                                        radius = 70f
                                    )
                                )
                                .padding(vertical = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(elementGradientColorLighter, elementGradientColorDarker),
                                            center = Offset(40f, 40f),
                                            radius = 70f
                                        )
                                    )
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Text("Generate", color = textColorDark)
                            }
                        }
                    }
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}
