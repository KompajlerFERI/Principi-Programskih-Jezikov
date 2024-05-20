package interface_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    onClickAddRestaurant: () -> Unit,
    onClickRestaurants: () -> Unit,
    onClickScraper: () -> Unit,
    onClickGenerate: () -> Unit,
    onClickAbout: () -> Unit
           ) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, neutralColorLight, RoundedCornerShape(10.dp))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(gradientColorLighter, gradientColorDarker),
                    center = Offset(322f, 582f),
                    radius = 720f
                )
            )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClickAddRestaurant() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add restaurants",
                    modifier = Modifier.size(20.dp),
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Add restaurant", color = textColor)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClickRestaurants() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "List all restaurants in a certain area",
                    modifier = Modifier.size(20.dp),
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Restaurants", color = textColor)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(19.dp))
        Divider(color = neutralColorLight, thickness = 1.1.dp)
        Spacer(modifier = Modifier.height(21.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClickScraper() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Get restaurants from the internet",
                    modifier = Modifier.size(20.dp),
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Scraper", color = textColor)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClickGenerate() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Generate a random restaurant",
                    modifier = Modifier.size(20.dp),
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "Generate", color = textColor)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Row(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClickAbout() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Get information about the backend application",
                    modifier = Modifier.size(20.dp),
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "About", color = textColor)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}