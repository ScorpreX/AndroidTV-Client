package com.unideb.fvass.letswatchit.core.data.repository

import com.unideb.fvass.letswatchit.core.remote.MovieService
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.domain.repositories.MovieRepository

class MovieRepositoryImp(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovieList(): List<Movie> {
        val response = movieService.getMovieList()
        if (response.isSuccessful) {
            val movieList = response.body()
            movieList?.let {
                return movieList
            }
        }
        return emptyList()
    }
}