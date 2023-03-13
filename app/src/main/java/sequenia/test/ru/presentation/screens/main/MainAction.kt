package sequenia.test.ru.presentation.screens.main

sealed interface MainAction {
    data class onClickMovie(val id: Int): MainAction
    data class ShowError(val message: String, val isNoConnectionError: Boolean) : MainAction
}