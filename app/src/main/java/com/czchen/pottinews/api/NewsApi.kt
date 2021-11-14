package com.czchen.pottinews.api

import com.czchen.pottinews.models.NewsResponse
import com.czchen.pottinews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/everything?q=apple&from=2021-11-13&to=2021-11-13&sortBy=popularity&apiKey=98ad4415fad24d95a3ca5b3db19dc230
interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNew(
        @Query("country")
        countryCode:String = "au",
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String = API_KEY
    ):Response<NewsResponse>
}