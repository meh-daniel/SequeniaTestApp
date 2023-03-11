package sequenia.test.ru.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import sequenia.test.ru.databinding.FragmentMainBinding
import sequenia.test.ru.presentation.screens.utils.observeInLifecycle

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscribers()
    }


    private fun setupSubscribers() {
        viewModel.state.onEach { state ->
            with(binding) {

                if(state is MainState.Loaded) ""
                //repositoryAdapter.submitList(state.repos)
                loadingView.visibility = if(state is MainState.Loading) View.VISIBLE else View.GONE

                if(state is MainState.Empty) {
                    Snackbar
                        .make(binding.root, "пусто", Snackbar.LENGTH_INDEFINITE)
                        .setAction("повторить") {

                        }
                        .show()
                }

                if(state is MainState.Error) {
                   if (state.isNoConnectionError) {
                       Snackbar
                           .make(binding.root, "пусто", Snackbar.LENGTH_INDEFINITE)
                           .setAction("повторить") {

                           }
                           .show()
                   } else {
                       Snackbar
                           .make(binding.root, "${state.message}", Snackbar.LENGTH_INDEFINITE)
                           .setAction("повторить") {

                           }
                           .show()
                   }
                }

            }
        }.observeInLifecycle(this)
    }

}