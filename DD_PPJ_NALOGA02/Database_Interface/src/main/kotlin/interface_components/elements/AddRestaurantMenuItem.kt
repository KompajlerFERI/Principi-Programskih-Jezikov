package interface_components.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import interface_components.textColor
import kotlinx.coroutines.delay
import scraper.Menu

@Composable
fun AddRestaurantMenuItem(
    menu: Menu,
    onEditClick: (Menu) -> Unit,
    onDeleteMenu: () -> Unit
) {
    // Extracting menu properties
    var editedMenuDish by remember { mutableStateOf(menu.dish) }
    var editedMenuExtras by remember { mutableStateOf(menu.extras.joinToString(", ")) }
    var editedMenuCategory by remember { mutableStateOf(menu.category) }

    val done = remember { mutableStateOf(false) }

    if (done.value || !done.value) {
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = editedMenuDish,
                        onValueChange = {
                            editedMenuDish = it
                        },
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
                        value = editedMenuCategory,
                        onValueChange = {
                            editedMenuCategory = it
                        },
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
                        value = editedMenuExtras,
                        onValueChange = {
                            editedMenuExtras = it
                        },
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
                            category = editedMenuCategory
                        )

                        onEditClick(editedMenu)
                        done.value = !done.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            tint = textColor
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
    }
}