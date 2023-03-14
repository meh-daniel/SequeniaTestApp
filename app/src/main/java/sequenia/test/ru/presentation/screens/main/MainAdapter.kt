package sequenia.test.ru.presentation.screens.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sequenia.test.ru.R
import sequenia.test.ru.databinding.ItemGenreBinding
import sequenia.test.ru.databinding.ItemMovieBinding
import sequenia.test.ru.databinding.ItemTitleBinding
import sequenia.test.ru.presentation.model.MovieUI

class MainAdapter(
    private val onClickMovie: (id: Int) -> Unit,
    private val onClickGenre: (genre: MovieUI.Genre) -> Unit,
) : ListAdapter<MovieUI, RecyclerView.ViewHolder>(MainAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType){
        R.layout.item_movie -> MovieHolder.from(parent, onClickMovie)
        R.layout.item_genre -> GenreHolder.from(parent, onClickGenre)
        R.layout.item_title -> TitleHolder.from(parent)
        else ->  throw Throwable("onCreateViewHolder exception - unknown view type by name: $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when(holder) {
        is MovieHolder -> holder.bind(item = getItem(position) as MovieUI.Movie)
        is GenreHolder -> holder.bind(item = getItem(position) as MovieUI.Genre)
        is TitleHolder -> holder.bind(item = getItem(position) as MovieUI.Title)
        else -> throw Throwable("onBindViewHolder exception - unknown holder of view by name ${holder.itemView.id}")
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)){
        is MovieUI.Movie -> R.layout.item_movie
        is MovieUI.Genre -> R.layout.item_genre
        is MovieUI.Title -> R.layout.item_title
        else -> throw Exception("getItemViewType unknown item class exception from position: $position")
    }

}

class MovieHolder(private val binding: ItemMovieBinding, private val onClickMovie: (id: Int) -> Unit) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: MovieUI.Movie){
        with(binding){
            titleFilm.text = item.localizedName
            if(item.imageUrl.isEmpty()){
                Glide
                    .with(img)
                    .load(R.drawable.not_found_image)
                    .into(img)
            } else {
                Glide
                    .with(img)
                    .load(item.imageUrl)
                    .into(img)
            }
        }
        onClick(item.id)
    }

    private fun onClick(id: Int) {
        binding.root.setOnClickListener {
            onClickMovie(id)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClickMovie: (id: Int) -> Unit): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieHolder(binding, onClickMovie)
        }
    }

}

class GenreHolder(private val binding: ItemGenreBinding,  private val onClickGenre: (genre: MovieUI.Genre) -> Unit) : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("ResourceAsColor")
    fun bind(item: MovieUI.Genre){
        with(binding){
            titleGenre.text = item.name
            if (item.clicked) {
                root.setBackgroundResource(R.color.color_clicked)
            }  else {
                root.setBackgroundResource(R.color.white)
            }
        }
        onClick(item)
    }

    private fun onClick(genre: MovieUI.Genre) {
        binding.root.setOnClickListener {
            onClickGenre(genre)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClickGenre: (genre: MovieUI.Genre) -> Unit): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)
            return GenreHolder(binding, onClickGenre)
        }
    }

}

class TitleHolder(private val binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: MovieUI.Title){
        with(binding){
            itemTitle.text = item.name
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTitleBinding.inflate(layoutInflater, parent, false)
            return TitleHolder(binding)
        }
    }

}

class MainAdapterDiffUtil: DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(
        oldItem: MovieUI,
        newItem: MovieUI
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MovieUI,
        newItem: MovieUI
    ): Boolean = oldItem == newItem

}