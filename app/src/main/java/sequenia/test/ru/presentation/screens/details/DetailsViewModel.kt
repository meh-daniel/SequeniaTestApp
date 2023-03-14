package sequenia.test.ru.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sequenia.test.ru.domain.MovieRepository
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    var state = _state.asStateFlow()

    private val _action: Channel<DetailsAction> = Channel(Channel.BUFFERED)
    var actionFlow = _action.receiveAsFlow()

    private var idMovie = 0

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getMovie(idMovie)
                setState(DetailsState.Loaded(data))
            } catch (e: Throwable) {
                errorSelection(e)
            }
        }
    }
    private fun errorSelection(e: Throwable) {
        when (e) {
            is ConnectException -> sendAction(DetailsAction.ShowError(e.message.toString(), true))
            else -> sendAction(DetailsAction.ShowError(e.message.toString(), false))
        }
    }

    private fun setState(state: DetailsState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = state
        }
    }

    private fun sendAction(action: DetailsAction){
        viewModelScope.launch(Dispatchers.Main){
            _action.send(action)
        }
    }

}