package com.unideb.fvass.letswatchit.feture_search.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.presentation.BackgroundState
import com.unideb.fvass.letswatchit.core.presentation.presenters.RowItemPresenter
import com.unideb.fvass.letswatchit.core.presentation.view.Constants
import com.unideb.fvass.letswatchit.core.presentation.viewModel.MovieListViewModel
import com.unideb.fvass.letswatchit.core.remote.Provider
import com.unideb.fvass.letswatchit.feature_movie.presentation.view.MovieActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
class SearchFragment : SearchSupportFragment(), SearchSupportFragment.SearchResultProvider {
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var movieAdapter: ArrayObjectAdapter
    private lateinit var listRowAdapter: ArrayObjectAdapter
    private lateinit var _movieList: List<Movie>
    private val backgroundState = BackgroundState(this)
    private var searchJob: Job? = null
    private var backgroundChangeJob: Job? = null
    private val movieListObserver = Observer<List<Movie>> {
        _movieList = it
    }


    private val onItemViewClickedListener = OnItemViewClickedListener { _, item, _, _ ->
        handleItemClick(item)
    }

    private val onItemViewSelectedListener = OnItemViewSelectedListener { _, item, _, _ ->
        handleItemViewSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        movieAdapter = ArrayObjectAdapter(ListRowPresenter())
        listRowAdapter = ArrayObjectAdapter(RowItemPresenter())

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backgroundState.loadUrl("https://gurulex.com/wp-content/uploads/2021/04/best-movies-2-scaled.jpg")
        observeMovieList()
        setSearchResultProvider(this)
        setOnItemViewClickedListener(onItemViewClickedListener)
        setOnItemViewSelectedListener(onItemViewSelectedListener)
    }

    override fun onDestroyView() {
        searchJob?.cancel()
        stopObserveMovieList()
        super.onDestroyView()
    }

    override fun getResultsAdapter(): ObjectAdapter {
        return movieAdapter
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
        onSearch(newQuery)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onSearch(query)
        return true
    }

    private fun onSearch(query: String?) {
        searchJob?.cancel()
        movieAdapter.clear()
        listRowAdapter = ArrayObjectAdapter(RowItemPresenter())
        searchJob = lifecycleScope.launch {
            _movieList.forEach { movie ->
                movie.title?.let { title ->
                    query?.let { query ->
                        if (title.contains(query, ignoreCase = true)) {
                            listRowAdapter.add(movie)
                        }
                    }
                }
            }
            movieAdapter.add(ListRow(id.toLong(), HeaderItem(query), listRowAdapter))
        }
    }

    private fun observeMovieList() {
        viewModel.movieList.observe(viewLifecycleOwner, movieListObserver)
    }

    private fun stopObserveMovieList() {
        viewModel.movieList.removeObserver(movieListObserver)
    }

    private fun handleItemClick(item: Any?) {
        when (item) {
            is Movie -> {
                val movie = item as? Movie
                movie?.let {
                    playMovie(it)
                }
            }
        }
    }

    private fun playMovie(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(Constants.EXTRA_MOVIE_DATA, movie)
        startActivity(intent)
    }

    private fun handleItemViewSelected(item: Any?) {
        when (item) {
            is Movie -> {
                val movie = item as? Movie
                movie?.let {
                    setBackground(it)
                }
            }
        }
    }

    private fun setBackground(movie: Movie) {
        val url = Provider.getImageURL(movie.id)
        backgroundChangeJob?.cancel()
        backgroundChangeJob = lifecycleScope.launch {
            delay(300)
            backgroundState.loadUrl(url)
        }
    }

}