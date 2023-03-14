package sequenia.test.ru.presentation.screens.details

import sequenia.test.ru.domain.model.Movie

interface DetailsState {
    object Loading: DetailsState
    data class Loaded(val data: Movie): DetailsState
}