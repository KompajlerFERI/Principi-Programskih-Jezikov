package interface_components.tabs

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import scraper.Restaurant
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import interface_components.*
import interface_components.elements.AddRestaurantMenuItem
import scraper.Menu
import util.ValidityUtil

@Composable
fun AddRestaurant(restaurants: MutableList<Restaurant>) {
    var restaurantName by remember { mutableStateOf("") }
    var fullPrice by remember { mutableStateOf("0.0") }
    var payPrice by remember { mutableStateOf("0.0") }
    var longitude by remember { mutableStateOf("0.0") }
    var latitude by remember { mutableStateOf("0.0") }
    var address by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var workingTimes: List<String> by remember { mutableStateOf(listOf("")) }

    var menuListUpdate by remember { mutableStateOf(false) }
    val menuList = remember { mutableStateOf(mutableListOf(Menu())) }

    var isValid by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.6f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            state = state
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Add Restaurant",
                    style = TextStyle(fontSize = 22.sp),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = restaurantName,
                    onValueChange = { restaurantName = it },
                    label = { Text("Restaurant Name") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = fullPrice,
                        onValueChange = {fullPrice = it },
                        label = { Text("Full Price") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    TextField(
                        value = payPrice,
                        onValueChange = {payPrice = it },
                        label = { Text("Pay Price") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = latitude,
                        onValueChange = {latitude = it },
                        label = { Text("Latitude") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    TextField(
                        value = longitude,
                        onValueChange = {longitude = it },
                        label = { Text("Longitude") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = textColor),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = textColor.copy(alpha = 0.3f),
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = address,
                    onValueChange = {address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = workingTimes.joinToString(", "),
                    onValueChange = { workingTimes = it.split(",") },
                    label = { Text("Working Times") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = textColor),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = textColor.copy(alpha = 0.3f),
                        focusedLabelColor = textColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.75f)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // TextFields for menu items
                Text(
                    text = "Menus:",
                    style = TextStyle(fontSize = 18.sp),
                    color = textColor
                )
                if (menuListUpdate || !menuListUpdate) {
                    menuList.value.forEach {  menu ->
                        AddRestaurantMenuItem(
                            menu = menu,
                            onUpdateMenu = {
                                val updatedMenus = menuList.value.toMutableList().apply { set(menuList.value.indexOf(menu), it) }
                                menuList.value = updatedMenus
                            },
                            onDeleteMenu = {
                                val updatedMenus = menuList.value.toMutableList().apply { remove(menu) }
                                menuList.value = updatedMenus
                            }
                        )

                    }
                }
                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = {
                        menuList.value.add(Menu("", mutableListOf(""), ""))
                        menuListUpdate = !menuListUpdate
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = textColor.copy(alpha = 0.3f)
                    )
                ) {
                    // Plus icon to add another menu
                    Icon(Icons.Default.Add, contentDescription = "Add Menu")
                }

                if (!isValid) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Input fields are not valid!",
                        style = TextStyle(fontSize = 14.sp),
                        color = errorColor
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                    if(
                        restaurantName.isNotEmpty() &&
                        fullPrice.toFloatOrNull() != null &&
                        payPrice.toFloatOrNull() != null &&
                        longitude.toFloatOrNull() != null &&
                        latitude.toFloatOrNull() != null &&
                        address.isNotEmpty()  &&
                        phoneNumber.isNotEmpty() &&
                        ValidityUtil.isValidPhoneNumber(phoneNumber) &&
                        workingTimes.isNotEmpty() &&
                        ValidityUtil.isValidWorkingHours(workingTimes.joinToString("\n"))
                    ) {
                        isValid = true
                        val restaurant = Restaurant(
                            name = restaurantName,
                            fullPrice = (fullPrice.toFloatOrNull() ?: 0.0f).toString(),
                            payPrice = (payPrice.toFloatOrNull() ?: 0.0f).toString(),
                            longitude = longitude,
                            latitude = latitude,
                            address = address,
                            phoneNumber = phoneNumber,
                            workingTimes = workingTimes.toMutableList(),
                            menuList = menuList.value,
                            scrapped = false
                        )
                        restaurants.add(restaurant)

                        // TODO: Add restaurant to database

                    }
                    else isValid = false
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, textColor, RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(elementGradientColorLighter, elementGradientColorDarker),
                                    center = Offset(40f, 40f),
                                    radius = 70f
                                )
                            )
                            .padding(vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(elementGradientColorLighter, elementGradientColorDarker),
                                        center = Offset(40f, 40f),
                                        radius = 70f
                                    )
                                )
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text("Add Restaurant", color = textColorDark)
                        }
                    }
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}