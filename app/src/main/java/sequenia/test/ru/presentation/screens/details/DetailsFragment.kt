package sequenia.test.ru.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import sequenia.test.ru.R
import sequenia.test.ru.databinding.FragmentDetailsBinding
import sequenia.test.ru.presentation.utils.observeInLifecycle

@AndroidEntryPoint
class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscriberState()
        setupSubscriberAction()
    }

    private fun setupSubscriberState() {
        viewModel.state.onEach { state ->
            with(binding) {
                if(state is DetailsState.Loaded) {

                }

                loadingView.visibility = if(state is DetailsState.Loading) View.VISIBLE else View.GONE
            }
        }.observeInLifecycle(this)
    }

    private fun setupSubscriberAction() {
        viewModel.actionFlow.onEach { action ->
            when(action) {
                is DetailsAction.NavigateBackToMain -> {
                    findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
                }
                is DetailsAction.ShowError -> {
                    if (action.isNoConnectionError) {
                        Snackbar
                            .make(binding.root, getString(R.string.empty), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.repeat)) {
                                viewModel.loadData()
                            }
                            .show()
                    } else {
                        Snackbar
                            .make(binding.root, action.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.repeat)) {
                                viewModel.loadData()
                            }
                            .show()
                    }
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

}