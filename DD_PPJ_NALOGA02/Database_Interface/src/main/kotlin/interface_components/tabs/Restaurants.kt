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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import interface_components.*
import interface_components.elements.ShowRestaurants
import okhttp3.OkHttpClient
import okhttp3.Request
import scraper.Restaurant
import scraper.RestaurantList
import scraper.*
import util.DatabaseJsonToClass
import java.io.IOException

@Composable
fun Restaurants(restaurants: MutableList<Restaurant>, isLoading: MutableState<Boolean>) {
    val client = OkHttpClient()

    var request = Request.Builder()
        .url("http://localhost:3001/restaurants")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println("=================================RESTAURANTS=================================")
        val responseBody = response.body!!.string()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        stringList.forEach {
            DatabaseJsonToClass.JsonToRestaurantClass(it)
        }
    }

    request = Request.Builder()
        .url("http://localhost:3001/menus")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println("=================================MENUS=================================")
        val responseBody = response.body!!.string()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        stringList.forEach {
            DatabaseJsonToClass.JsonToMenuItem(it)
        }
    }

    request = Request.Builder()
        .url("http://localhost:3001/tags")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println("=================================TAGS=================================")
        val responseBody = response.body!!.string()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        stringList.forEach {
            DatabaseJsonToClass.JsonTagToClass(it)
        }

        for (tag in TagList.tags) {
            println("TAG: ${tag.name}")
        }
    }




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
                        val restaurantsToRemove = mutableListOf<Restaurant>()

                        for (restaurant in restaurants) {
                            if (!RestaurantList.restaurants.any { it.name == restaurant.name }) {
                                if (restaurant.deleted) {
                                    restaurantsToRemove.add(restaurant)
                                    println("Restaurant was supposed to be gone")
                                } else {
                                    RestaurantList.restaurants.add(restaurant)
                                }
                            } else {
                                println("Restaurant already exists in the list.")
                            }
                        }
                        restaurants.removeAll(restaurantsToRemove)
                        restaurantsToRemove.clear()

                        ShowRestaurants(state = state, restaurants = RestaurantList.restaurants, refresh = refresh)

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