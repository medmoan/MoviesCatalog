package com.example.netflixclone.movies.data.remote.api

import com.example.netflixclone.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}