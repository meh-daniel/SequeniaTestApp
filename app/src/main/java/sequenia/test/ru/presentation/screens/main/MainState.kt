package sequenia.test.ru.presentation.screens.main

import sequenia.test.ru.presentation.model.MovieUI

sealed interface MainState {
    object Loading: MainState
    data class Loaded(val data: List<MovieUI>): MainState
}