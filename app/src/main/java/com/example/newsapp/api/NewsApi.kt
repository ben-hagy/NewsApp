package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The basic way these functions work is you use a top level annotation (GET, PUT, DELETE, etc.), and
 * then provide additional parameters to the constructor of your suspend functions via the @Query
 * annotation, where the string passed to the annotation matches the query parameter of the URL for
 * the API you're using, and then the following parameter is how we'll interact with/change what is
 * passed into that parameter.
 *
 * Ex: @Query("country") <-- url might be "https://place.org/v2/top-headlines/country=us
 * countryCode: String = "us" <-- the is the default value we'll pass
 */

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}