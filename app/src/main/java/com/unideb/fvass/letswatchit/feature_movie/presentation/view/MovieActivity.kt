package com.unideb.fvass.letswatchit.feature_movie.presentation.view

import MovieFragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.util.UnstableApi
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.presentation.view.Constants
import com.unideb.fvass.letswatchit.databinding.ActivityMovieBinding

@UnstableApi class MovieActivity : FragmentActivity() {
    private var binding: ActivityMovieBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_MOVIE_DATA, Movie::class.java)
        } else {
            intent.getParcelableExtra(Constants.EXTRA_MOVIE_DATA)
        }

        val bundle = Bundle()
        bundle.putParcelable(Constants.EXTRA_MOVIE_DATA, movie)

        val fragment = MovieFragment()
        fragment.arguments = bundle


        binding?.mainMovieFragment?.let {
            supportFragmentManager.beginTransaction().replace(it.id, fragment).commitNow()
        }
    }
}