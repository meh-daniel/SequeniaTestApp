package sequenia.test.ru.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sequenia.test.ru.data.MovieRepositoryImpl
import sequenia.test.ru.data.nw.MovieApi
import sequenia.test.ru.domain.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideSessionRepository(
        api: MovieApi
    ) : MovieRepository {
        return MovieRepositoryImpl(api)
    }
}