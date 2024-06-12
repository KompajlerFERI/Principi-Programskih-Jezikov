package interface_components.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.textColor
import scraper.Menu
import scraper.RestaurantList
import util.PushToDatabase

@Composable
fun MenuItem(
    menu: Menu,
    onEditClick: (Menu) -> Unit,
    onDeleteClick: () -> Unit
) {
    var editedMenuDish by remember { mutableStateOf(menu.dish.replace("\n", "")) }
    var editedMenuExtras by remember { mutableStateOf(menu.extras.joinToString(", ")) }
    var editedMenuCategory by remember { mutableStateOf(menu.category) }

    val editMenu = remember { mutableStateOf(false) }

    if (!editMenu.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 15.dp, 0.dp, 0.dp)
        ) {
            // Display fields
            Column(
                modifier = Modifier
                    .weight(9f)
            ) {
                Text(
                    text = editedMenuDish + "\n" + editedMenuExtras + "\n" + editedMenuCategory,
                    color = textColor,
                    style = TextStyle(fontSize = 13.sp)
                )
            }

            // Edit button
            Column(
                modifier = Modifier
                    .weight(1.2f)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(onClick = {
                    editMenu.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }

            // Delete button
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
            ) {
                IconButton(onClick = { onDeleteClick() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
            ) {
                IconButton(
                    onClick = {
                        if (RestaurantList.restaurants.any { it.id == menu.restaurantId && it.isInDatabase }) {
                            PushToDatabase.pushMenuToDatabase(menu, menu.restaurantId)
                        }
                    },
                    enabled = RestaurantList.restaurants.any { it.id == menu.restaurantId && it.isInDatabase }
                ) {
                    val checkColor = if (menu.isInDatabase && menu.edited) {
                        val hexColor = "F29DAB"
                        val red = hexColor.substring(0, 2).toInt(16)
                        val green = hexColor.substring(2, 4).toInt(16)
                        val blue = hexColor.substring(4, 6).toInt(16)
                        Color(red, green, blue)
                    }
                    else if (menu.isInDatabase) {
                        textColor
                    }
                    else {
                        Color.Gray
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = checkColor
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        ) {
            // Editable fields
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(3.dp)
            ) {
                TextField(
                    value = editedMenuDish,
                    onValueChange = { editedMenuDish = it },
                    label = { Text("Dish") }
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(3.dp)
            ) {
                TextField(
                    value = editedMenuExtras,
                    onValueChange = { editedMenuExtras = it },
                    label = { Text("Extras") }
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(3.dp)
            ) {
                TextField(
                    value = editedMenuCategory,
                    onValueChange = { editedMenuCategory = it },
                    label = { Text("Category") }
                )
            }

            Spacer(modifier = Modifier.weight(10f))

            // Done with editing button
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 0.dp, 5.dp, 0.dp)
            ) {
                IconButton(onClick = {
                    val editedMenu = Menu(
                        dish = editedMenuDish,
                        extras = editedMenuExtras.split(", ").toMutableList(),
                        category = editedMenuCategory,
                        restaurantId = menu.restaurantId,
                        id = menu.id,
                        isInDatabase = menu.isInDatabase,
                        edited = true
                    )

                    onEditClick(editedMenu)
                    editMenu.value = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }
        }
    }
}
