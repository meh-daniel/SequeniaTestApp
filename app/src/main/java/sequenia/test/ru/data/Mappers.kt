package sequenia.test.ru.data

import android.net.Uri
import sequenia.test.ru.data.nw.model.MovieNW
import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie

private const val BASE_URL_IMAGES = "https://st.kp.yandex.net/images/film_iphone/"

internal fun MovieNW.toDomain(): List<Movie> {
    return films?.map {
        Movie(
            description = (if(it!!.description.isNullOrBlank()) "" else it.description)!!,
            genres = if(it.genres!!.isNotEmpty()) {
                it.genres.map {
                    Genre(name = it!!)
                }
            } else mutableListOf(),
            id = it.id!!,
            imageUrl = if(it.imageUrl.isNullOrBlank()) "" else it.imageUrl.correctnessURl(),
            localizedName = if(it.localizedName.isNullOrBlank()) "" else it.localizedName,
            name = if(it.name.isNullOrBlank()) "" else it.name,
            rating = if(it.rating.isNullOrBlank()) 0.0 else it.rating.toDouble(),
            year = it.year
        )
    } ?: mutableListOf()
}

private fun String.correctnessURl(): String {
    return BASE_URL_IMAGES + Uri.parse(this).lastPathSegment.toString()
}
