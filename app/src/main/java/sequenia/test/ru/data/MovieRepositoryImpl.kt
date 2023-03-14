package sequenia.test.ru.data

import sequenia.test.ru.data.nw.MovieApi
import sequenia.test.ru.domain.MovieRepository
import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.domain.model.Movie

class MovieRepositoryImpl(
    private val api: MovieApi
): MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return alphabeticalSorting(api.getRepositories().toDomain())
    }

    override suspend fun getMovies(genre: Genre): List<Movie> {
        return alphabeticalSorting(selectMovieBy(api.getRepositories().toDomain(), genre))
    }

    override suspend fun getMovie(id: Int): Movie {
        return searchMovie(api.getRepositories().toDomain(), id)
    }

    override suspend fun getGenre(): List<Genre> {
        return selectGenre(api.getRepositories().toDomain())
    }

    private fun selectMovieBy(films: List<Movie>, genre: Genre): List<Movie> {
        return films.filter { it.genres.contains(genre) }
    }

    private fun selectGenre(films: List<Movie>): List<Genre> {
        val data = mutableListOf<Genre>()
        films.map {
            it.genres.map {
                if(!data.contains(it)){
                    data.add(it)
                }
            }
        }
        return data
    }

    private fun searchMovie(films: List<Movie>,id: Int): Movie {
        lateinit var movie: Movie
        films.map { if(it.id == id) movie = it }
        return movie
    }

    private fun alphabeticalSorting(films: List<Movie>): List<Movie> {
        return films.sortedBy { it.localizedName }
    }

}