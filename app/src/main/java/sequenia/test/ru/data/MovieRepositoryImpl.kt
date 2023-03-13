package sequenia.test.ru.data

import sequenia.test.ru.data.nw.MovieApi
import sequenia.test.ru.domain.MovieRepository
import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie

class MovieRepositoryImpl(
    private val api: MovieApi
): MovieRepository {

    override suspend fun get(): List<Movie> {
        return api.getRepositories().toDomain()
    }

    override suspend fun get(genre: Genre): List<Movie> {
        return selectMovieBy(api.getRepositories().toDomain(), genre)
    }

    override suspend fun getGenre(): List<Genre> {
        return selectGenre(api.getRepositories().toDomain())
    }

    private fun selectMovieBy(films: List<Movie>, genre: Genre): List<Movie> {
        return films.filter { it.genres.contains(genre) }
    }

    private fun selectGenre(films: List<Movie>): List<Genre> {
        var data = mutableListOf<Genre>()
        films.map {
            it.genres.map {
                if(!data.contains(it)){
                    data.add(it)
                }
            }
        }
        return data
    }

}