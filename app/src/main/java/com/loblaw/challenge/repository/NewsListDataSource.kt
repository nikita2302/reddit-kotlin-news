package com.loblaw.challenge.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.loblaw.challenge.Constants
import com.loblaw.challenge.apis.RetrofitBuilder
import com.loblaw.challenge.model.Children
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsListDataSource(private val scope: CoroutineScope) :
    ItemKeyedDataSource<String, Children>() {
    lateinit var key: String

    val newsListErrorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<Children>
    ) {
        scope.launch {
            when (val newsResponse = RetrofitBuilder.safeApiCall {
                RetrofitBuilder.newsAPIs.getNews(null)
            }) {
                is com.loblaw.challenge.apis.Result.Success -> {
                    key = newsResponse.data.data.after
                    callback.onResult(newsResponse.data.data.children)
                }
                is com.loblaw.challenge.apis.Result.ErrorString -> {
                    newsListErrorMessage.value = Constants.ERROR
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<Children>
    ) {
        scope.launch {
            when (val newsResponse = RetrofitBuilder.safeApiCall {
                RetrofitBuilder.newsAPIs.getNews(key)
            }) {
                is com.loblaw.challenge.apis.Result.Success -> {
                    key = newsResponse.data.data.after
                    callback.onResult(newsResponse.data.data.children)
                }
                is com.loblaw.challenge.apis.Result.ErrorString -> {
                    newsListErrorMessage.value = Constants.ERROR
                }
            }
        }
    }

    /**
     * No need to override load before since the recycler view will cache the data
     * This has also been proved by testing while scrolling up data is always present
     * and shows up on the screen in correct order
     */
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Children>) {

    }

    override fun getKey(item: Children): String {
        return key
    }
}