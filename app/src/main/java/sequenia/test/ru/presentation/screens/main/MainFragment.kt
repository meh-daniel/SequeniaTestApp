package sequenia.test.ru.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import sequenia.test.ru.R
import sequenia.test.ru.databinding.FragmentMainBinding
import sequenia.test.ru.utils.observeInLifecycle

private const val ID_MOVIE = "id_movie"

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val mainAdapter = MainAdapter(
        onClickMovie = { viewModel.clickOnMovie(it) },
        onClickGenre = { viewModel.clickOnGenre(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
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
        initMainRecycler()
    }


    private fun setupSubscriberState() {
        viewModel.state.onEach { state ->
            with(binding) {
                if(state is MainState.Loaded) mainAdapter.submitList(state.data)
                loadingView.visibility = if(state is MainState.Loading) View.VISIBLE else View.GONE
                content.visibility = if(state is MainState.Loaded) View.VISIBLE else View.GONE
            }
        }.observeInLifecycle(this)
    }

    private fun setupSubscriberAction() {
        viewModel.actionFlow.onEach { action ->
            when(action) {
                is MainAction.NavigateToDetails -> {
                    val bundle = Bundle()
                    bundle.putString(ID_MOVIE, action.movieID.toString())
                    findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
                }
                is MainAction.ShowError -> {
                    if (action.isNoConnectionError) {
                        Snackbar
                            .make(binding.root, getString(R.string.empty), Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.repeat)) {
                                viewModel.loadMovie()
                            }
                            .show()
                    } else {
                        Snackbar
                            .make(binding.root, action.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getString(R.string.repeat)) {
                                viewModel.loadMovie()
                            }
                            .show()
                    }
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun initMainRecycler() {
        with(binding) {
            filmsRv.adapter = mainAdapter
            val manager =  GridLayoutManager(
                this@MainFragment.context,
                4
            )
            manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when {
                        binding.filmsRv
                            .adapter?.getItemViewType(position) == R.layout.item_genre -> 4
                        binding.filmsRv
                            .adapter?.getItemViewType(position) == R.layout.item_title -> 4
                        else -> 2
                    }
                }
            }
            filmsRv.layoutManager = manager
        }
    }

}