package interface_components.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import interface_components.textColor

@Composable
fun ErrorContent() {
    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Error: Something went wrong",
            style = TextStyle(
                fontSize = 22.sp
            ),
            color = textColor
        )
    }
}