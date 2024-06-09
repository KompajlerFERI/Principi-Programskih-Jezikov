package scraper

class Tag(var name: String = "",
          var id: String = "",
          var isInDatabase: Boolean = false) {}

object TagList {
    val tags = mutableListOf<Tag>()

    fun add(tag: Tag) =
        tags.add(tag)
}