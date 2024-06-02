package util

import androidx.compose.runtime.Composable
import scraper.Menu

object CalculateUtil {
    fun calculateHeight(size: Int, multiplyBy: Int): Int {
        return size * multiplyBy
    }

}