package sequenia.test.ru.domain

import sequenia.test.ru.domain.model.Genre

interface MovieRepository {

    suspend fun get()

    suspend fun get(genre: Genre)

}