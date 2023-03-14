package sequenia.test.ru.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import sequenia.test.ru.databinding.FragmentDetailsBinding

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
//        viewModel.state.onEach { state ->
//            with(binding) {
//                if(state is MainState.Loaded) mainAdapter.submitList(state.data)
//                loadingView.visibility = if(state is MainState.Loading) View.VISIBLE else View.GONE
//            }
//        }.observeInLifecycle(this)
    }

    private fun setupSubscriberAction() {
//        viewModel.actionFlow.onEach { action ->
//            when(action) {
//                is MainAction.NavigateToDetails -> {
//                    val bundle = Bundle()
//                    bundle.putString(ID_REPO, id.toString())
//                    findNavController().navigate(R.id.action_repositoriesListFragment_to_detailInfoFragment, bundle)
//                }
//                is MainAction.ShowError -> {
//                    if (action.isNoConnectionError) {
//                        Snackbar
//                            .make(binding.root, getString(R.string.empty), Snackbar.LENGTH_INDEFINITE)
//                            .setAction(getString(R.string.repeat)) {
//                                viewModel.loadMovie()
//                            }
//                            .show()
//                    } else {
//                        Snackbar
//                            .make(binding.root, "${action.message}", Snackbar.LENGTH_INDEFINITE)
//                            .setAction(getString(R.string.repeat)) {
//                                viewModel.loadMovie()
//                            }
//                            .show()
//                    }
//                }
//            }
//        }.observeInLifecycle(viewLifecycleOwner)
    }


}