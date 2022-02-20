package com.rivas.testandroiddeveloper.ui.main.movies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivas.testandroiddeveloper.data.Movie
import com.rivas.testandroiddeveloper.databinding.MovieRvViewBinding
import com.rivas.testandroiddeveloper.ui.main.movies.MoviesViewModel
import com.rivas.testandroiddeveloper.utils.extensions.loadImage
import com.rivas.testandroiddeveloper.utils.extensions.toDate

class MovieAdapter(
    private val viewModel: MoviesViewModel,
) :
    RecyclerView.Adapter<MainViewHolder>() {

    private var movieList = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movie: List<Movie>) {
        this.movieList = movie.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieRvViewBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movieList[position]
        (movie.originalTitle).also { holder.binding.tvName.text = it }
        (movie.voteAverage).also { holder.binding.textViewProgress.text = ((it*10).toInt()).toString() }
        (movie.voteAverage).also { holder.binding.progressBar.progress = (it*10).toInt() }
        (movie.releaseDate).let {
            if (it != null) {
                holder.binding.tvReleaseDate.text = it.toDate()
            } else
                holder.binding.tvReleaseDate.text = "".toDate()
        }
        movie.posterPath?.let { holder.binding.imgMovie.loadImage(it) }
        if (movieList.size - 20 == position)
            viewModel.nextPage()

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class MainViewHolder(val binding: MovieRvViewBinding) : RecyclerView.ViewHolder(binding.root)