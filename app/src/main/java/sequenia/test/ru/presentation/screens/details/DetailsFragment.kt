package sequenia.test.ru.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import sequenia.test.ru.R
import sequenia.test.ru.databinding.FragmentDetailsBinding
import sequenia.test.ru.utils.observeInLifecycle

private const val ID_MOVIE = "id_movie"

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
        arguments?.getString(ID_MOVIE)?.let {
            viewModel.idMovie = it.toInt()
        }
        viewModel.loadData()
        setupSubscriberState()
        setupSubscriberAction()
        setupListeners()
    }

    private fun setupSubscriberState() {
        viewModel.state.onEach { state ->
            with(binding) {
                if (state is DetailsState.Loaded) {
                    nameMovie.text = if(state.data.localizedName.isNotBlank()) state.data.localizedName else state.data.name
                    title.text = if(state.data.localizedName.isNotBlank()) state.data.localizedName else state.data.name
                }
                genresMovie.text = if(state is DetailsState.Loaded) {
                    var ui = ""
                    state.data.genres.map {
                        ui += "${it.name}, "
                    }
                    ui += "${state.data.year} ${getString(R.string.year)}"
                    ui
                } else ""
                if (state is DetailsState.Loaded && state.data.imageUrl.isNotEmpty()) {
                    Glide
                        .with(img)
                        .load(state.data.imageUrl)
                        .into(img)
                } else {
                    Glide
                        .with(img)
                        .load(R.drawable.not_found_image)
                        .into(img)
                }
                ratingNumber.text = if(state is DetailsState.Loaded) state.data.rating.toString() else getString(R.string.zero_zero)
                description.text = if(state is DetailsState.Loaded && state.data.description.isNotBlank()) state.data.description else getString(R.string.no_description)
                loadingView.visibility = if(state is DetailsState.Loading) View.VISIBLE else View.GONE
                content.visibility = if(state is DetailsState.Loaded) View.VISIBLE else View.GONE
            }
        }.observeInLifecycle(this)
    }

    private fun setupSubscriberAction() {
        viewModel.actionFlow.onEach { action ->
            when(action) {
                is DetailsAction.NavigateBackToMain -> {
                    findNavController().popBackStack()
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

    private fun setupListeners() {
        binding.vector.setOnClickListener {
            viewModel.moveBack()
        }
    }

}