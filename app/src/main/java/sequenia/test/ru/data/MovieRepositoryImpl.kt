package sequenia.test.ru.data

import sequenia.test.ru.domain.MovieRepository
import sequenia.test.ru.domain.model.Genre

class MovieRepositoryImpl: MovieRepository {

    override suspend fun get() {
        TODO("Not yet implemented")
    }

    override suspend fun get(genre: Genre) {
        TODO("Not yet implemented")
    }

}