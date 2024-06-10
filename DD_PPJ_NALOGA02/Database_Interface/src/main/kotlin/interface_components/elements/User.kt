package interface_components.elements

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.gradientColorDarker
import interface_components.gradientColorLighter
import interface_components.textColor
import scraper.User
import util.PushToDatabase
import util.RemoveFromDatabase

@Composable
@Preview
fun User(
    refresh: MutableState<Boolean>,
    restaurantsTemp: MutableList<User>,
    user: User,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                    text = user.username,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = user.firstName,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = user.lastName,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = user.email,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = user.userType,
                    color = textColor,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }
            // Delete button
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
            ) {
                IconButton(onClick = { onDeleteClick(); }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }
        }
    }
}
