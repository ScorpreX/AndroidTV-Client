package com.unideb.fvass.letswatchit.core.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unideb.fvass.letswatchit.core.remote.RetrofitInstance
import com.unideb.fvass.letswatchit.core.data.repository.MovieRepositoryImp
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.domain.repositories.MovieRepository
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
    private val _movieList = MutableLiveData<MutableList<Movie>>()
    val movieList: LiveData<MutableList<Movie>>
        get() = _movieList

    private lateinit var movieRepository: MovieRepository

    init {
        initRepository()
        loadMovieList()
    }

    private fun initRepository() {
        movieRepository = MovieRepositoryImp(RetrofitInstance.movieApi)
    }

    private fun loadMovieList() {
        viewModelScope.launch {
            _movieList.postValue(movieRepository.getMovieList().toMutableList())
        }
    }
}