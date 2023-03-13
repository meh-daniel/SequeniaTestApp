package sequenia.test.ru.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sequenia.test.ru.R
import sequenia.test.ru.di.StringResourcesProvider
import sequenia.test.ru.domain.MovieRepository
import sequenia.test.ru.domain.model.Genre
import sequenia.test.ru.presentation.model.MovieUI
import sequenia.test.ru.presentation.toDomain
import sequenia.test.ru.presentation.toUI
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourcesProvider: StringResourcesProvider,
    private val repository: MovieRepository
): ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    var state = _state.asStateFlow()

    private val _action: Channel<MainAction> = Channel(Channel.BUFFERED)
    var actionFlow = _action.receiveAsFlow()

    private var currentGenre: Genre = Genre(resourcesProvider.getString(R.string.empty))

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data: List<MovieUI> =
                    mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.genres))) +
                            repository.getGenre().toUI() +
                            mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.movie))) +
                            repository.get().toUI()
                setState(MainState.Loaded(data))
            } catch (e: Throwable) {
                errorSelection(e)
            }
        }
    }

    fun clickOnGenre(genre: MovieUI.Genre) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (currentGenre == genre.toDomain()){
                    val data: List<MovieUI> =
                        mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.genres))) +
                                repository.getGenre().toUI() +
                                mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.movie))) +
                                repository.get().toUI()
                    setState(MainState.Loaded(data))
                    currentGenre = Genre(resourcesProvider.getString(R.string.empty))
                } else {
                    val data: List<MovieUI> =
                        mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.genres))) +
                                repository.getGenre().toUI(genre.toDomain()) +
                                mutableListOf(MovieUI.Title(resourcesProvider.getString(R.string.movie))) +
                                repository.get(genre.toDomain()).toUI()
                    setState(MainState.Loaded(data))
                    currentGenre = genre.toDomain()
                }
            } catch (e: Throwable) {
                errorSelection(e)
            }
        }
    }

    fun clickOnMovie(id: Int) {
        sendAction(MainAction.onClickMovie(id))
    }

    private fun errorSelection(e: Throwable) {
        when (e) {
            is ConnectException -> sendAction(MainAction.ShowError(e.message.toString(), true))
            else -> sendAction(MainAction.ShowError(e.message.toString(), false))
        }
    }

    private fun setState(state: MainState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = state
        }
    }

    private fun sendAction(action: MainAction){
        viewModelScope.launch(Dispatchers.Main){
            _action.send(action)
        }
    }

}