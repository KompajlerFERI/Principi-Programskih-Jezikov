package scraper

class Restaurant(
    var name : String = "",
    var fullPrice: String = "",
    var payPrice: String = "",
    var longitude: String = "",
    var latitude: String = "",
    var address: String = "",
    var menuListString: MutableList<String> = mutableListOf(), //tu samo shrani menije kot string, tega ne uporablaj nikjer ker je useless
    var menuList: MutableList<Menu> = mutableListOf(), //tu ko jih razcepi na Menu jih da v seznam
    var phoneNumber : String = "",
    var workingTimes: MutableList<String> = mutableListOf(), // vsi so čisto isto zapisani, eni majo sobota/nedelja: zaprto
) {
    override fun toString(): String {
        var menuString = ""
        menuList.forEach() { menu ->
            menuString += menu.toString() + "\n"
        }

        return "RESTAURANT: $name\n" +
                "Full price: $fullPrice\n" +
                "Pay price: $payPrice\n" +
                "Longitude: $longitude\n" +
                "Latitude: $latitude\n" +
                "Address: $address\n" +
                "Phone number: $phoneNumber\n" +
                "Working times: $workingTimes\n\n" +
                "MENU:\n" +
                menuString + "\n"
    }

}