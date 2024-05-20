package content

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val primaryColor = Color(0xFF00FFFF)
val secondaryColor = Color(0xFF00FFFF)
@Composable
@Preview
fun NavBar(onClickInvoices: () -> Unit, onClickAbout: () -> Unit) {
    Surface(color = primaryColor) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clickable { onClickInvoices() }
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Invoices")
            }

        }
    }
}