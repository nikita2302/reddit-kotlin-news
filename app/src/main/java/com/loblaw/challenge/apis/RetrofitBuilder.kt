package com.loblaw.challenge.apis

import com.loblaw.challenge.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    /**
     * Custom Interceptor to add headers to all requests
     * similarly can also add authentication and error handling interceptors
     */
    private val customHeaderInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            return chain.proceed(request)
        }
    }

    //default call timeout is 10 seconds but getting images from network might take time.
    private val okHttp = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .callTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(customHeaderInterceptor)
        .addInterceptor(logger)

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
    }

    val newsAPIs: NewsAPIService by lazy {
        retrofitBuilder.build().create(NewsAPIService::class.java)
    }

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> retrofit2.Response<T>): Result<T> {
        return try {
            val myResp = apiCall.invoke()

            if(myResp.isSuccessful) {

                /**
                 * Currently the API does not have an empty response body.
                 * Hence this condition won't be required.
                 * I have added it here to showcase how I will handle 200 and 204 responses.
                 */
                if (myResp.code() == 204 || (myResp.code() == 201 && myResp.body() == null)) {
                    return Result.SuccessNoBody(" " + myResp.code() + " " + myResp.message())
                }

                return Result.Success(myResp.body()!!)
            } else {
                /**
                 * Currently, it is an open API therefore it won't have an authorization error.
                 * However I have added this here to showcase how error handling will work.
                 * Other HTTP Codes can also be added here for example 403, 500 etc.
                 */
                if (myResp.code() == 401) {
                    return Result.ErrorString(Constants.UNAUTHORIZED)
                }

                return Result.ErrorString(" " + myResp.code() + " " + myResp.message())
            }

        } catch (e: Exception) {
            Result.ErrorString(Constants.NETWORK_ERROR)
        }
    }
}