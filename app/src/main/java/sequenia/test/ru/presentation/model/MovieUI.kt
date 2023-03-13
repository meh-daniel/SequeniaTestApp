package sequenia.test.ru.presentation.model

sealed class MovieUI {

    data class Movie(
        val id: Int,
        val imageUrl: String,
        var localizedName: String = "",
        val name: String,
    ): MovieUI()

    data class Genre(
        val name: String,
        val clicked: Boolean = false
    ): MovieUI()

    data class Title(
        val name: String
    ): MovieUI()

}