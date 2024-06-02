package util

object ValidityUtil {
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^(\\(0[1-7]\\)\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}|\\(0[1-7]{2}\\)\\s?\\d{3}\\s?\\d{3}|\\(0[1-7]{2}\\)\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}|\\(0[1-7]{3}\\)\\s?\\d{2}\\s?\\d{3}|\\d{3}\\s?\\d{2}\\s?\\d{2}|\\d{3}\\s?\\d{3}\\s?\\d{3}|(030|040|068|069|031|041|051|065|070|071|064|059|081|082|083|080|089|090|112|113)\\s?\\d{6})\$"))
    }
    fun isValidWorkingHours(workingHours: String): Boolean {
        return workingHours.matches(Regex("((Ponedeljek|Torek|Sreda|Četrtek|Petek|Sobota)[ ]{0,3}:[ ]{0,3}\\d{2}[:.]\\d{2}[ ]{0,3}-[ ]{0,3}\\d{2}[:.]\\d{2}\\n){6}Nedelja[ ]{0,3}:[ ]{0,3}\\d{2}[:.]\\d{2}[ ]{0,3}-[ ]{0,3}\\d{2}[:.]\\d{2}"))
    }
}