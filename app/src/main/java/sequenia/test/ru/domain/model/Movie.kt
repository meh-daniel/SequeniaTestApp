package sequenia.test.ru.domain.model

data class Movie(
    val description: String,
    val genres: List<Genre>,
    var id: Int = 0,
    val imageUrl: String,
    var localizedName: String = "",
    val name: String,
    var rating: Double = 0.0,
    val year: Int?
)
