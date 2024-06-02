package interface_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import interface_components.tabs.*
import scraper.Restaurant

@Composable
fun ContentSwitcher(
    clicked: MutableState<Boolean>,
    restaurants: MutableList<Restaurant>,
    isLoading: MutableState<Boolean>,
    currentContent: ContentSwitch
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, neutralColorLight, RoundedCornerShape(10.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(gradientColorLighter, gradientColorDarker),
                    center = Offset(12f, 2f),
                    radius = 720f
                )
            )
    ) {
        if (clicked.value || !clicked.value ) {
            when (currentContent) {
                ContentSwitch.AddRestaurants -> AddRestaurant(restaurants)
                ContentSwitch.Restaurants -> Restaurants(restaurants, isLoading)
                ContentSwitch.Scraper -> Scraper(restaurants, isLoading)
                ContentSwitch.Generate -> Generate(restaurants)
                ContentSwitch.About -> About()
                ContentSwitch.ErrorContent -> ErrorContent()
            }
        }
        else Restaurants(restaurants, isLoading)
    }
}