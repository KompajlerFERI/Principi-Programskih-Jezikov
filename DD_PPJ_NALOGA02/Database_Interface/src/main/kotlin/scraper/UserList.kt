package scraper



object UserList {
    val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }
}