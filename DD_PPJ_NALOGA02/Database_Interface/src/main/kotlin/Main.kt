import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Window

import content.NavBar
import content.primaryColor

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    Scaffold (
        topBar = {
            Surface(
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(155.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xff00eaff), Color(0xff008bd6)),
                                center = Offset(322f, 582f),
                                radius = 720f
                            )
                        )
                        .border(1.dp, Color(0xFFedf4f5), RoundedCornerShape(10.dp)),
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {  }
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add restaurants",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Add restaurant", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {  }
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "List all restaurants in a certain area",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Restaurants", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(9.dp))
                    Divider(color = Color(0xFFedf4f5), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(11.dp))
                    Row(
                        modifier = Modifier
                            .clickable {  }
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Get restaurants from the internet",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Scraper", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .clickable {  }
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Generate a random restaurant",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Generate", color = Color.White)
                    }
                }
            }
        }

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),
            contentAlignment = Alignment.TopCenter,

            ) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
