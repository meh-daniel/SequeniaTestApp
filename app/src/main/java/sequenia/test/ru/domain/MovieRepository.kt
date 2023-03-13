package sequenia.test.ru.domain

import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie

interface MovieRepository {

    suspend fun get(): List<Movie>

    suspend fun get(genre: Genre): List<Movie>

    suspend fun getGenre(): List<Genre>

}