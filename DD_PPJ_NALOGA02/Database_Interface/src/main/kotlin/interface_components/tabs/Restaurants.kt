package interface_components.tabs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import interface_components.*
import interface_components.elements.ShowRestaurants
import scraper.Restaurant

@Composable
fun Restaurants(restaurants: MutableList<Restaurant>, isLoading: MutableState<Boolean>) {
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
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Transparent)
                    .padding(10.dp)
            ) {
                if (isLoading.value) {
                    Spacer(modifier = Modifier.height(7.dp))
                    CircularProgressIndicator(
                        color = textColor,
                        strokeWidth = 5.dp,
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.Center)
                    )
                } else {
                    val state = rememberLazyListState()
                    val refresh = remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(Color.Transparent)
                            .padding(10.dp)
                            .padding(bottom = 30.dp)
                    ) {
                        ShowRestaurants(state = state, restaurants = restaurants, refresh = refresh)

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