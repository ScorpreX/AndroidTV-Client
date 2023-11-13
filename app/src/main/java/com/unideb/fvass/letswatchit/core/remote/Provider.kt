package com.unideb.fvass.letswatchit.core.remote

import com.unideb.fvass.letswatchit.core.presentation.view.Constants

object Provider {
    private const val PROTOCOL = NetworkConstants.PROTOCOL
    private const val IP_ADDRESS = NetworkConstants.BASE_URL
    private const val PORT = NetworkConstants.PORT_NUMBER
    private const val MOVIE_ENDPOINT = Constants.MOVIE_ENDPOINT

    fun getImageURL(movieID: Int): String {
        return "${buildURL(movieID)}/${Constants.IMAGE_ENDPOINT}"
    }

    fun getMovieURL(movieID: Int): String {
        return buildURL(movieID)
    }

    private fun buildURL(movieID: Int): String {
        return "$PROTOCOL://$IP_ADDRESS:$PORT/$MOVIE_ENDPOINT/$movieID"
    }
}