package interface_components.elements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import scraper.User

@Composable
fun ShowUsers(
    state: LazyListState,
    users: MutableList<User>,
    refresh: MutableState<Boolean>
) {
    if (refresh.value || !refresh.value) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(end = 12.dp),
            state
        ) {
            items(
                items = users
            )  { restaurant ->
                User(
                    refresh = refresh,
                    restaurantsTemp = users,
                    user = restaurant,
                    onDeleteClick = {
                        users.remove(restaurant)
                        refresh.value = !refresh.value
                    }
                )
            }
        }
    }
}