package com.rivas.testandroiddeveloper.ui.main.movies

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.data.Movie
import com.rivas.testandroiddeveloper.databinding.FragmentMoviesBinding
import com.rivas.testandroiddeveloper.repository.room.movie.MovieRepository
import com.rivas.testandroiddeveloper.ui.main.movies.adapter.MovieAdapter
import com.rivas.testandroiddeveloper.utils.extensions.observer
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MoviesFragment : DaggerFragment() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var moviesRepository: MovieRepository

    private lateinit var adapter: MovieAdapter

    private val movies = ArrayList<Movie>()

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        createObservers()
        setupList()
        setupTitle()
        moviesViewModel.validateLocalData()

        return binding.root
    }

    private fun setupTitle() {
        requireActivity().setTitle(R.string.title_movies)
    }

    private fun setupList() {
        adapter = MovieAdapter(moviesViewModel)
        binding.rvMovies.adapter = adapter
    }

    private fun addMovies(moviesAux: List<Movie>) {
        if(moviesAux.isNotEmpty() && moviesAux[0].page==moviesViewModel.page.toString())
            this.movies.addAll(moviesAux)
        adapter.setMovies(this.movies)
    }

    private fun createObservers() {
        observerLocalMovies()
        observerLoader()
        observerError()
    }

    private fun observerError() {
        moviesViewModel._error.observer(viewLifecycleOwner, {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(it.localizedMessage)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            builder.create()
            builder.show()
        })
    }

    private fun observerLoader() {
        moviesViewModel.addObserver.observer(viewLifecycleOwner, {
            if(it)
                observerMovies()
        })
    }

    private fun observerLocalMovies() {
        moviesViewModel.movies.observer(viewLifecycleOwner, {
            addMovies(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observerMovies() {
        moviesRepository.findByPage(moviesViewModel.page.toString())
            .observer(viewLifecycleOwner) {
                addMovies(it)
                moviesRepository.findByPage(moviesViewModel.page.toString())
                    .removeObservers(viewLifecycleOwner)
            }
    }
}