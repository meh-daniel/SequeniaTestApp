package sequenia.test.ru.data.nw

import retrofit2.http.GET
import sequenia.test.ru.data.nw.model.MovieNW

interface MovieApi {

    @GET("sequeniatesttask/films.json")
    suspend fun getRepositories() : MovieNW

}