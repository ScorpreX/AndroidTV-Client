import android.os.Build
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.leanback.LeanbackPlayerAdapter
import com.unideb.fvass.letswatchit.core.remote.Provider
import com.unideb.fvass.letswatchit.R
import com.unideb.fvass.letswatchit.core.domain.models.Movie
import com.unideb.fvass.letswatchit.core.presentation.view.Constants

@UnstableApi
class MovieFragment : VideoSupportFragment() {
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerAdapter: LeanbackPlayerAdapter
    private lateinit var playbackTransportControlGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.EXTRA_MOVIE_DATA, Movie::class.java)
        } else {
            arguments?.getParcelable(Constants.EXTRA_MOVIE_DATA)
        } ?: throw IllegalStateException("No movie data available")

        initializePlayer(movie)
    }

//    override fun onPause() {
//        super.onPause()
////        if (playbackTransportControlGlue.isPlaying || exoPlayer.isPlaying) {
////            playbackTransportControlGlue.pause()
////            exoPlayer.pause()
////        }
//    }

    private fun initializePlayer(movie: Movie) {
        setupExoplayer(movie)
        setupAdapter()
        setupTransportGlue()
        exoPlayer.play()
    }

    private fun setupExoplayer(movie: Movie)
    {
        val mediaSource = buildMediaItemAndSource(movie)
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    private fun setupAdapter() {
        playerAdapter = LeanbackPlayerAdapter(requireContext(), exoPlayer, Constants.UPDATE_PERIODS)
        playerAdapter.setProgressUpdatingEnabled(true)

    }

    private fun setupTransportGlue() {
        playbackTransportControlGlue = PlaybackTransportControlGlue(requireContext(), playerAdapter)
        playbackTransportControlGlue.host = VideoSupportFragmentGlueHost(this)
//        playbackTransportControlGlue.addPlayerCallback(object: PlaybackGlue.PlayerCallback(){
//            override fun onPreparedStateChanged(glue: PlaybackGlue?) {
////                super.onPreparedStateChanged(glue)
//                if (glue != null) {
//                    if(glue.isPrepared){
//
//                    }
//                }
//            }
//        })
        playbackTransportControlGlue.title = movie.title
        playbackTransportControlGlue.isSeekEnabled = true
        playbackTransportControlGlue.playWhenPrepared()

    }

    private fun buildMediaItemAndSource(movie: Movie): ProgressiveMediaSource {
        val url = Provider.getMovieURL(movie.id)
        val mediaItem = MediaItem.fromUri(url)
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(Util.getUserAgent(requireContext(), getString(R.string.app_name)))
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }


    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}