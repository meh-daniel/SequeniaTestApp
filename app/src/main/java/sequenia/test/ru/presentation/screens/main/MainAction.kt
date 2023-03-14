package sequenia.test.ru.presentation.screens.main

sealed interface MainAction {
    data class NavigateToDetails(val movieID: Int): MainAction
    data class ShowError(val message: String, val isNoConnectionError: Boolean) : MainAction
}