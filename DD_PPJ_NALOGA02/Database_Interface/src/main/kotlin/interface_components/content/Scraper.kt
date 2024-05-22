package interface_components.content

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scraper.Restaurant
import scraper.RestaurantList
import scraper.Scraper

@Composable
fun Scraper(restaurants: MutableState<List<Restaurant>>, isLoading: MutableState<Boolean>) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading.value = true
                        val scrapedRestaurants = withContext(Dispatchers.IO) {
                            val scraper = Scraper()
                            scraper.scrape()
                            RestaurantList.restaurants
                        }
                        restaurants.value = scrapedRestaurants
                        isLoading.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, textColor, RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(gradientColorLighter, gradientColorDarker),
                            center = Offset(50f, 50f),
                            radius = 200f
                        )
                    )
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
                    Text(
                        "Scrape",
                        style = TextStyle(
                            fontSize = 19.sp,
                            brush = Brush.radialGradient(
                                colors = listOf(innerElementsTextColorLighter, innerElementsTextColorDarker),
                                center = Offset(30f, 30f),
                                radius = 60f
                            ),
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(0f, 0f),
                                blurRadius = 0.3f
                            )
                        )

                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            var customPadding = if (isLoading.value) 72.dp else 0.dp
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .padding(bottom = customPadding)
            ) {
                if (isLoading.value) {
                    Spacer(modifier = Modifier.height(7.dp))
                    CircularProgressIndicator(
                        color = textColor,
                        strokeWidth = 5.dp,
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.Center)
                            .padding(bottom = 35.dp)
                    )
                }
                else {
                    val state = rememberLazyListState()
                    val refresh = remember { mutableStateOf(false) }
                    val edit = remember { mutableStateOf(false) }
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(end = 12.dp),
                        state
                    ) {
                        items(restaurants.value) { restaurant ->
                            RestaurantItem(
                                restaurant = restaurant,
                                onClickDelete = {
                                    restaurants.value = restaurants.value.toMutableList().apply { remove(restaurant) }
                                    refresh.value = !refresh.value
                                },
                                onEditClick = { editedRestaurant ->
                                    // Find and update the edited restaurant
                                    val index = restaurants.value.indexOf(restaurant)
                                    if (index != -1) {
                                        val updatedList = restaurants.value.toMutableList()
                                        updatedList[index] = editedRestaurant
                                        restaurants.value = updatedList
                                        refresh.value = !refresh.value
                                    }
                                }
                            )
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
        }
    }
}