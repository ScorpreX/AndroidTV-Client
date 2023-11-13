package com.unideb.fvass.letswatchit.core.remote

import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.presentation.view.Constants
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET("/${Constants.MOVIE_ENDPOINT}")
    suspend fun getMovieList() : Response<List<Movie>>
}