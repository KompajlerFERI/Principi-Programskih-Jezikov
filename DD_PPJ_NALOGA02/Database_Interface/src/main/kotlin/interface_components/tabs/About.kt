package interface_components.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import interface_components.textColor
import util.HyperlinkUtil

@Composable
fun About() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            ),
            text= "About application"
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            style = TextStyle(
                fontSize = 20.sp,
                color = textColor
            ),
            text= "Project done by the group Kompajler."
        )


        Spacer(modifier = Modifier.height(6.dp))
        Text(
            style = TextStyle(
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = textColor
            ),
            text= "The group consists of Nikola Popovski,"
        )
        Text(
            style = TextStyle(
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = textColor
            ),
            text= "Jan Vališer and"
        )
        Text(
            style = TextStyle(
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = textColor
            ),
            text= "Aljoša Golob."
        )
        Text(
            style = TextStyle(
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = textColor
            ),
            text= "Mentored by Uroš Mlakar."
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            style = TextStyle(
                fontSize = 12.sp,
                color = textColor,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            ),
            text= ""
        )
        HyperlinkUtil.HyperlinkText(
            fullText = "The data is scrapped from the website studentska prehrana.",
            linkText = listOf("studentska prehrana"),
            hyperlinks = listOf("https://www.studentska-prehrana.si/"),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            style = TextStyle(
                fontSize = 14.sp,
                color = textColor,
                textAlign = TextAlign.Center
            ),
            text= "This is a project for the subject Principles of programming languages.\n" +
                    "The lead of the seminar project of the subject is assistant Matej Moravec.\n" +
                    "The project is a web application that allows users to add restaurants to the database,\n" +
                    "view the restaurants in the database, scrape the web for new restaurants and generate\n" +
                    "a random restaurant or a random restaurant list."
        )
    }
}