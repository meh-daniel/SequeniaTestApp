package sequenia.test.ru.domain

import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie

interface MovieRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovies(genre: Genre): List<Movie>

    suspend fun getMovie(id: Int): Movie

    suspend fun getGenre(): List<Genre>

}