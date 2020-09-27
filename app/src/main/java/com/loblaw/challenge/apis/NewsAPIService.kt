package com.loblaw.challenge.apis

import com.loblaw.challenge.model.NewsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("/r/Kotlin/.json")
    suspend fun getNews(@Query("after") after: String?): Response<NewsList>
}