package com.unideb.fvass.letswatchit.core.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofitClient: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("${NetworkConstants.PROTOCOL}://" +
                    "${NetworkConstants.BASE_URL}:${NetworkConstants.PORT_NUMBER}")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val movieApi: MovieService by lazy {
        retrofitClient.create(MovieService::class.java)
    }
}