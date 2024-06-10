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
import interface_components.elements.ShowUsers
import okhttp3.OkHttpClient
import okhttp3.Request
import scraper.RestaurantList
import scraper.*
import util.DatabaseJsonToClass
import java.io.IOException

@Composable
fun Users() {
    val isLoading = remember { mutableStateOf(false) }
    val users = remember { mutableStateListOf<User>() }
    val client = OkHttpClient()

    var request = Request.Builder()
        .url("http://localhost:3001/users")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        println("=================================USERS=================================")
        val responseBody = response.body!!.string()

        val listType = object : TypeToken<List<Map<String, Any>>>() {}.type
        val list: List<Map<String, Any>> = Gson().fromJson(responseBody, listType)
        val stringList = list.map { Gson().toJson(it) }

        stringList.forEach {
            DatabaseJsonToClass.JsonToUser(it)
        }

        for (user in UserList.users) {
            println(user.username)
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
                        val usersToRemove = mutableListOf<User>()

                        users.removeAll(usersToRemove)
                        usersToRemove.clear()

                        ShowUsers(state = state, users = UserList.users, refresh = refresh)

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