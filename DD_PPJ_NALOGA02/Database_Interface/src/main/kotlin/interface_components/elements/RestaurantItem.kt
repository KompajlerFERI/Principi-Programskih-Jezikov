package interface_components.elements

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.gradientColorDarker
import interface_components.gradientColorLighter
import interface_components.textColor
import scraper.Restaurant
import util.CalculateUtil
import util.PushToDatabase
import util.RemoveFromDatabase
import util.ValidityUtil

@Composable
@Preview
fun RestaurantItem(
    refresh: MutableState<Boolean>,
    restaurant: Restaurant,
    onEditClick: (Restaurant) -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val editRestaurant = remember { mutableStateOf(false) }
    val editMenu = remember { mutableStateOf(false) }

    // Define mutable state variables for editable fields
    var editedName by remember { mutableStateOf(restaurant.name) }
    var editedAddress by remember { mutableStateOf(restaurant.address) }
    var editedFullPrice by remember { mutableStateOf(restaurant.fullPrice) }
    var editedLongitude by remember { mutableStateOf(restaurant.longitude) }
    var editedLatitude by remember { mutableStateOf(restaurant.latitude) }
    var editedPayPrice by remember { mutableStateOf(restaurant.payPrice) }
    var editedPhoneNumber by remember { mutableStateOf(restaurant.phoneNumber) }
    var editedWorkingTimes by remember { mutableStateOf(restaurant.workingTimes.joinToString("\n")) }

    val menus = remember { mutableStateOf(restaurant.menuList) }

    // Everything is inside this column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                1.dp, Brush.radialGradient(
                    colors = listOf(gradientColorLighter, gradientColorDarker),
                    center = Offset(50f, 50f),
                    radius = 200f
                ), RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(gradientColorLighter, gradientColorDarker),
                    center = Offset(50f, 50f),
                    radius = 700f
                )
            )
            .background(Color.White.copy(alpha = 0.3f))
            .clickable { expanded = !expanded }
    ) {
        // If not editing, show restaurant fields
        if (!editRestaurant.value) {
            // Row with restaurant name, address, pay price, phone number, dropdown, edit and delete buttons
            Row(
                modifier = Modifier.fillMaxWidth()
                    //.background(Color.LightGray)
            ) {
                // Display restaurant fields
                Column(
                    modifier = Modifier
                        .weight(10f)
                        .padding(16.dp)
                ) {
                    Text(
                        text = restaurant.name ,
                        color = textColor,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
//                            shadow = Shadow(
//                                color = Color.Black, offset = Offset(0f, 0f), blurRadius = 2f
//                            )
                        )
                    )
                    Text(
                        text = restaurant.address,
                        color = textColor,
                        style = TextStyle(
                            fontSize = 12.sp,
//                            shadow = Shadow(
//                                color = Color.Black, offset = Offset(0f, 0f), blurRadius = 2f
//                            )
                        )
                    )
                    Text(
                        text = restaurant.payPrice + " €",
                        color = textColor,
                        style = TextStyle(
                            fontSize = 14.sp,
//                            shadow = Shadow(
//                                color = Color.Black, offset = Offset(0f, 0f), blurRadius = 2f
//                            )
                        )
                    )
                    Text(
                        text = restaurant.phoneNumber,
                        color = textColor,
                        style = TextStyle(
                            fontSize = 12.sp,
//                            shadow = Shadow(
//                                color = Color.Black, offset = Offset(0f, 0f), blurRadius = 2f
//                            )
                        )
                    )
                }

                // Dropdown button
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                }

                // Edit button
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(onClick = {
                        editRestaurant.value = true
                        onEditClick(restaurant)
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
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                ) {
                    IconButton(onClick = { onDeleteClick(); RemoveFromDatabase.removeRestaurant(restaurant) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                }

                // Is in database / inst in database
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                ) {
                    IconButton(onClick = {PushToDatabase.pushRestaurant(restaurant)}) {
                        val checkColor = if (restaurant.isInDatabase) {
                            textColor
                        } else {
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
        }
        // If editing, show editable fields
        else {
            // Editable restaurant fields
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(10f)
                        .padding(16.dp)
                ) {
                    // Display editable fields
                    TextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Name") }
                    )
                    TextField(
                        value = editedAddress,
                        onValueChange = { editedAddress = it },
                        label = { Text("Address") }
                    )
                    TextField(
                        value = editedPayPrice,
                        onValueChange = { editedPayPrice = it },
                        label = { Text("Pay Price") }
                    )
                    TextField(
                        value = editedFullPrice,
                        onValueChange = { editedFullPrice = it },
                        label = { Text("Full Price") }
                    )
                    TextField(
                        value = editedLatitude,
                        onValueChange = { editedLatitude = it },
                        label = { Text("Latitude") }
                    )
                    TextField(
                        value = editedLongitude,
                        onValueChange = { editedLongitude = it },
                        label = { Text("Longitude") }
                    )
                    TextField(
                        value = editedPhoneNumber,
                        onValueChange = { editedPhoneNumber = it },
                        label = { Text("Phone Number") }
                    )
                    TextField(
                        value = editedWorkingTimes,
                        onValueChange = { editedWorkingTimes = it },
                        label = { Text("Working times") }
                    )
                }

                // Confirm changes button
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(onClick = {
                        if(
                            editedName.length > 0 &&
                            editedPayPrice.toFloatOrNull() != null &&
                            editedAddress.length > 0 &&
                            editedPhoneNumber.length > 0 &&
                            ValidityUtil.isValidPhoneNumber(editedPhoneNumber) &&
                            editedWorkingTimes.length > 0 &&
                            ValidityUtil.isValidWorkingHours(editedWorkingTimes)
                        ) {
                            // Create a new restaurant object with edited fields
                            val editedRestaurant = Restaurant(
                                name = editedName,
                                address = editedAddress,
                                payPrice = editedPayPrice,
                                fullPrice = editedFullPrice,
                                latitude = editedLatitude,
                                longitude = editedLongitude,
                                phoneNumber = editedPhoneNumber,
                                workingTimes = editedWorkingTimes.split("\n").toMutableList(),
                                menuList = menus.value.toMutableList(),
                                scrapped = restaurant.scrapped
                            )

                            onEditClick(editedRestaurant) // Call the edit callback with the edited restaurant
                            expanded = false // Collapse the item after editing
                            editRestaurant.value = false
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                    IconButton(onClick = {
                        editRestaurant.value = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = textColor
                        )
                    }
                }
            }
        }

        // If expanded, show working times and menu
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
            ) {
                // Display working times
                Text(
                    text = "Delavni čas:\n" + restaurant.workingTimes.joinToString("\n"),
                    color = textColor,
                    style = TextStyle(
                        fontSize = 12.sp,
//                        shadow = Shadow(
//                            color = Color.Black, offset = Offset(0f, 0f), blurRadius = 2f
//                        )
                    )
                )


                // Display menu using LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .height(
                            CalculateUtil.calculateHeight(
                                restaurant.menuList.size,
                                68
                            ).dp
                        )
                ) {
                    items(menus.value) { menu ->
                        MenuItem(
                            menu = menu,
                            onEditClick = { editedMenu ->
                                val index = menus.value.indexOf(menu)
                                if (index != -1) {
                                    val updatedMenus = menus.value.toMutableList()
                                    updatedMenus[index] = editedMenu
                                    menus.value = updatedMenus
                                    restaurant.menuList = updatedMenus
                                    editMenu.value = false
                                }
                            },
                            onDeleteClick = {
                                val updatedMenus = menus.value.toMutableList().apply { remove(menu) }
                                menus.value = updatedMenus
                                restaurant.menuList = updatedMenus
                            }
                        )
                    }
                }
            }
        }
    }
}
