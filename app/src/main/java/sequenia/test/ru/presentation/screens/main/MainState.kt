package sequenia.test.ru.presentation.screens.main

sealed interface MainState {

    object Idle: MainState
    object Loading: MainState
    data class Loaded(val data: String): MainState
    data class Error(val message: String, val isNoConnectionError: Boolean) : MainState
    object Empty : MainState

}