package sequenia.test.ru.presentation.screens.details

interface DetailsAction {
    object NavigateBackToMain: DetailsAction
    data class ShowError(val message: String, val isNoConnectionError: Boolean) : DetailsAction
}