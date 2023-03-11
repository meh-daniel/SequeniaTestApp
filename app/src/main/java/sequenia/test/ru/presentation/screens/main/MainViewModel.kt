package sequenia.test.ru.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException

class MainViewModel: ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    var state = _state.asStateFlow()


    init {
        loadRepositories()
    }

    fun loadRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            setState(MainState.Loading)
            try {
                val repos = ""
                if(repos.isEmpty()) {
                    setState(MainState.Empty)
                } else {
                    setState(MainState.Loaded(repos))
                }
            } catch (e: Throwable) {
                when (e) {
                    is ConnectException -> setState(MainState.Error(e.message.toString(), true))
                    else -> setState(MainState.Error(e.message.toString(), false))
                }
            }
        }
    }

    private fun setState(state: MainState) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = state
        }
    }



}