package interface_components.tabs

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
import interface_components.elements.RestaurantItem
import interface_components.elements.ShowRestaurants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scraper.Restaurant
import scraper.RestaurantList
import scraper.Scraper

@Composable
fun Scraper(restaurants: MutableList<Restaurant>, isLoading: MutableState<Boolean>) {
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
            val enabled = remember { mutableStateOf(true) }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                enabled = enabled.value,
                onClick = {
                    restaurants.removeIf({ it.scrapped && !it.isInDatabase })
                    RestaurantList.restaurants.removeIf({ it.scrapped && !it.isInDatabase })


                    coroutineScope.launch {
                        isLoading.value = true

                        val scrapedRestaurants = withContext(Dispatchers.IO) {
                            val scraper = Scraper()
                            enabled.value = false
                            scraper.scrape()
                            enabled.value = true
                            RestaurantList.restaurants
                        }


                        restaurants.addAll(scrapedRestaurants)

                        restaurants.removeIf { restaurant ->
                            restaurants.count { it.name == restaurant.name && it.isInDatabase } > 0 && !restaurant.isInDatabase
                        }
                        RestaurantList.restaurants.removeIf { restaurant ->
                            RestaurantList.restaurants.count { it.name == restaurant.name && it.isInDatabase } > 0 && !restaurant.isInDatabase
                        }

                        isLoading.value = false
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
                        Text(
                            "Scrape",
                            color = textColorDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val customPadding = if (isLoading.value) 72.dp else 0.dp
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
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Color.Transparent)
                            .padding(10.dp)
                            .padding(bottom = 30.dp)
                    ) {
                        ShowRestaurants(state = state, restaurants = RestaurantList.restaurants, refresh = refresh, showScrappedOnly = true)

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
}