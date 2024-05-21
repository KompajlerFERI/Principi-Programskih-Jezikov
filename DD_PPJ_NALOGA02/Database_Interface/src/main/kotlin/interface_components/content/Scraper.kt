package interface_components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.gradientColorDarker
import interface_components.gradientColorLighter
import interface_components.textColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scraper.Menu
import scraper.Restaurant
import scraper.RestaurantList
import scraper.Scraper

@Composable
fun Scraper() {
    var restaurants by remember { mutableStateOf(listOf<Restaurant>()) }
    var isLoading by remember { mutableStateOf(false) }
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
            Spacer(modifier = Modifier.height(2.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        val scrapedRestaurants = withContext(Dispatchers.IO) {
                            val scraper = Scraper()
                            scraper.scrape()
                            RestaurantList.restaurants
                        }
                        restaurants = scrapedRestaurants
                        isLoading = false
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = textColor),
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Brush.radialGradient(
                        colors = listOf(gradientColorLighter, gradientColorDarker),
                        center = Offset(50f, 50f),
                        radius = 200f
                    ), RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.radialGradient(
                                colors = listOf(gradientColorLighter, gradientColorDarker),
                                center = Offset(50f, 50f),
                                radius = 200f
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp) // Adjust padding as needed
                ) {
                    Text(
                        "Scrape",
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = textColor
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator(color = textColor)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(restaurants) { restaurant ->
                        RestaurantItem(
                            restaurant = restaurant,
                            onEditClick = {
                                // Handle edit click, for now just print the restaurant's name
                                println("Edit: ${it.name}")
                            },
                        )
                    }
                }
            }
        }
    }
}