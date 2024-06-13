import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import interface_components.*
import io.github.serpro69.kfaker.Faker
import okhttp3.OkHttpClient
import okhttp3.Request
import scraper.Restaurant
import scraper.RestaurantList
import util.DatabaseJsonToClass
import java.io.IOException

@Composable
@Preview
fun App() {
    val clickedNavButton = remember { mutableStateOf(false) }
    var currentContent by remember { mutableStateOf(ContentSwitch.About) }
    var restaurants = remember { mutableListOf<Restaurant>() }
    val isLoading = remember { mutableStateOf(false) }

    for (restaurant in RestaurantList.restaurants) {
        if (!restaurants.any { it.name == restaurant.name }) {
            restaurants.add(restaurant)
        } else {
            println("Restaurant already exists in the list.")
        }
    }

    // Znotraj row-a je nav bar in content stran ob strani
    Row(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(backgroundGradientColorLighter, backgroundGradientColorDarker),
                    center = Offset(122f, 282f),
                    radius = 1220f
                )
            )
            .padding(10.dp)
    ) {
        NavBar(
            onClickAddRestaurant = {
                currentContent = ContentSwitch.AddRestaurants
                clickedNavButton.value = !clickedNavButton.value
            },
            onClickRestaurants = {
                currentContent = ContentSwitch.Restaurants
                clickedNavButton.value = !clickedNavButton.value
            },
            onClickScraper = {
                currentContent = ContentSwitch.Scraper
                clickedNavButton.value = !clickedNavButton.value
            },
            onClickGenerate = {
                currentContent = ContentSwitch.Generate
                clickedNavButton.value = !clickedNavButton.value
            },
            onClickAbout = {
                currentContent = ContentSwitch.About
                clickedNavButton.value = !clickedNavButton.value
            },
            onClickUsers = {
                currentContent = ContentSwitch.Users
                clickedNavButton.value = !clickedNavButton.value
            }
        )
        Spacer(modifier = Modifier.fillMaxHeight().width(15.dp))
        ContentSwitcher(
            clicked = clickedNavButton,
            restaurants = restaurants,
            isLoading = isLoading,
            currentContent = currentContent
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Studentska prehrana vmesnik") {
        App()
    }
}
