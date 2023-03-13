package sequenia.test.ru.presentation.screens.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import sequenia.test.ru.R
import sequenia.test.ru.databinding.FragmentMainBinding
import sequenia.test.ru.presentation.model.MovieUI
import sequenia.test.ru.presentation.utils.observeInLifecycle

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val mainAdapter = MainAdapter(
        onClickMovie = { Log.d("xxx123", "${it}") },
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubscribers()
        initMainRecycler()
    }


    private fun setupSubscribers() {
        viewModel.state.onEach { state ->
            with(binding) {

                if(state is MainState.Loaded) mainAdapter.submitList(state.data)
                loadingView.visibility = if(state is MainState.Loading) View.VISIBLE else View.GONE


            }
        }.observeInLifecycle(this)
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