package interface_components.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import interface_components.textColor
import scraper.Menu

@Composable
fun AddRestaurantMenuItem(
    menu: Menu,
    onUpdateMenu: (Menu) -> Unit,
    onDeleteMenu: () -> Unit
) {
    // Extracting menu properties
    var dish by remember { mutableStateOf(menu.dish) }
    var category by remember { mutableStateOf(menu.category) }
    var extras by remember { mutableStateOf(menu.extras.joinToString(", ")) }

    // Update menu properties when text fields change
    DisposableEffect(menu) {
        onDispose {
            onUpdateMenu(menu.copy(dish = dish, category = category, extras = extras.split(", ").toMutableList()))
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = dish,
                    onValueChange = { dish = it },
                    label = { Text("Dish Name") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = extras,
                    onValueChange = { extras = it },
                    label = { Text("Extras") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )
            }

            Button(
                onClick = onDeleteMenu,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp, 18.dp, 8.dp, 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = textColor.copy(alpha = 0.3f)
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Menu", tint = textColor)
            }
        }
    }
}
