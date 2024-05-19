package scraper

class Menu(var dish: String = "",
           var extras: MutableList<String> = mutableListOf(),
           var category: String = ""
) {
    fun menuStringToMenu(menuString: String) : Menu {
        val menuSplitIntoParts = menuString.split(";")
        val dish = menuSplitIntoParts[0]
        val category = menuSplitIntoParts[1]
        val extrasString = menuSplitIntoParts[2].split("%%%")
        extrasString.forEach() { extra ->
            if (extra != "") {
                extras.add(extra)
            }
        }

        return Menu(dish, extras, category)
    }

    override fun toString(): String {
        var extraString = ""
        extras.forEach() { extra ->
            extraString += "$extra, "
        }
        extraString.removeSuffix(", ")

        return "$dish|" +
                "$category\n" +
                "Extras: $extraString"
    }
}