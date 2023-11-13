package com.unideb.fvass.letswatchit.core.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import com.unideb.fvass.letswatchit.core.presentation.BackgroundState
import com.unideb.fvass.letswatchit.core.remote.Provider
import com.unideb.fvass.letswatchit.R
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.presentation.presenters.RowItemPresenter
import com.unideb.fvass.letswatchit.core.presentation.viewModel.MovieListViewModel
import com.unideb.fvass.letswatchit.feature_movie.presentation.view.MovieActivity
import com.unideb.fvass.letswatchit.feture_search.presentation.view.SearchActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
class MainFragment : BrowseSupportFragment() {
    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var rowAdapter: ArrayObjectAdapter
    private lateinit var movieListAdapter: ArrayObjectAdapter
    private var job: Job? = null
    private val backgroundState = BackgroundState(this)

    private val movieListObserver = Observer<List<Movie>> {
        loadMovies(it)
    }

    private fun startMovieListObserver() {
        viewModel.movieList.observe(viewLifecycleOwner, movieListObserver)
    }

    private fun stopObserveMovieList() {
        viewModel.movieList.removeObserver(movieListObserver)
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
        rowAdapter = ArrayObjectAdapter(ListRowPresenter())
        movieListAdapter = ArrayObjectAdapter(RowItemPresenter())

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        startMovieListObserver()

        setOnItemViewClickedListener(onItemViewClickedListener)
        setOnItemViewSelectedListener(onItemViewSelectedListener)
    }

    private fun setupUI() {
//        title = getString(R.string.app_name)
//        badgeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.app_icon_your_movie, null)
        badgeDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.app_icon_your_movie)

        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = false

        val header = HeaderItem(Constants.HEADER_TITLE)
        rowAdapter.add(ListRow(header, movieListAdapter))
        this.adapter = rowAdapter


        setOnSearchClickedListener {
            Intent(requireContext(), SearchActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun loadMovies(movieList: List<Movie>) {
        movieListAdapter.clear()
        movieListAdapter.addAll(0, movieList)
    }

    private fun handleItemViewSelected(item: Any?) {
        when (item) {
            is Movie -> {
                val movie = item as? Movie
                movie?.let {
                    setBackground(it)
                }
            }
            else -> {
                backgroundState.loadUrl(Constants.DEFAULT_BG)
            }
        }
    }

    private fun setBackground(movie: Movie) {
        val url = Provider.getImageURL(movie.id)
        job?.cancel()
        job = lifecycleScope.launch {
            delay(300)
            backgroundState.loadUrl(url)
        }
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

    override fun onDestroyView() {
        stopObserveMovieList()
        job?.cancel()
        job = null
        super.onDestroyView()
    }

//    private fun updateBackground(uri: String?) {
//        val width = mMetrics.widthPixels
//        val height = mMetrics.heightPixels
//        Glide.with(requireContext()).load(uri).centerCrop().error(mDefaultBackground)
//            .into<SimpleTarget<Drawable>>(object : SimpleTarget<Drawable>(width, height) {
//                override fun onResourceReady(
//                    drawable: Drawable, transition: Transition<in Drawable>?
//                ) {
//                    mBackgroundManager.drawable = drawable
//                }
//            })
//        mBackgroundTimer?.cancel()
//    }
}