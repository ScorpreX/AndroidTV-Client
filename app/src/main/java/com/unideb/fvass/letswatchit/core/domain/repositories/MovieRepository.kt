package com.unideb.fvass.letswatchit.core.domain.repositories

import com.unideb.fvass.letswatchit.core.domain.models.Movie

interface MovieRepository {
    suspend fun getMovieList(): List<Movie>
}