package util

import java.util.regex.Pattern

object RestaurantUtil {
    fun isValidWorkingTimes(input: String): Boolean {
        val regex = """
        ^Ponedeljek : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Torek : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Sreda : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Četrtek : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Petek : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Sobota : \d{2}:\d{2} - \d{2}:\d{2}$\n
        ^Nedelja : \d{2}:\d{2} - \d{2}:\d{2}$
    """.trimIndent()
        return Pattern.compile(regex, Pattern.MULTILINE).matcher(input).matches()
    }
}