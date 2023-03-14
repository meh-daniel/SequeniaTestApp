package sequenia.test.ru.presentation

import android.util.Log
import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie
import sequenia.test.ru.presentation.model.MovieUI

internal fun List<Movie>.toUI(): List<MovieUI> {
    return map {
        MovieUI.Movie(
            id = it.id,
            imageUrl = it.imageUrl,
            localizedName = it.localizedName,
            name = it.name,
        )
    }
}

@JvmName("toUIGenre")
internal fun List<Genre>.toUI(): List<MovieUI.Genre> {
    return map {
        MovieUI.Genre(
            name = it.name,
            clicked = false
        )
    }
}

@JvmName("toUIGenre")
internal fun List<Genre>.toUI(genre: Genre): List<MovieUI.Genre> {
    return map {
        if (it == genre) {
            Log.d("xxx123", "yes $it")
            MovieUI.Genre(
                name = it.name,
                clicked = true
            )
        } else {
            Log.d("xxx123", "no $it")
            MovieUI.Genre(
                name = it.name,
                clicked = false
            )
        }
    }
}

internal fun MovieUI.Genre.toDomain(): Genre {
    return Genre(
        name = name
    )
}