package com.loblaw.challenge.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.loblaw.challenge.Constants
import com.loblaw.challenge.model.Children
import com.loblaw.challenge.repository.NewsListDataSource

class NewsListViewModel : ViewModel(){

    var newsPagedList: LiveData<PagedList<Children>>
    var newsPagedListError : LiveData<String>
    lateinit var newsListDataSource: NewsListDataSource

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(Constants.RECYCLER_VIEW_PAGE_SIZE)
            .setInitialLoadSizeHint(Constants.RECYCLER_VIEW_PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        val dataSourceFactory = object : DataSource.Factory<String, Children>() {

            val newsPagedData = MutableLiveData<ItemKeyedDataSource<String, Children>>()
            lateinit var newsPagedDataSource: NewsListDataSource

            override fun create(): DataSource<String, Children> {
                newsPagedDataSource = NewsListDataSource(viewModelScope)
                newsListDataSource = newsPagedDataSource
                newsPagedData.postValue(newsPagedDataSource)

                return newsPagedDataSource
            }
        }

        newsPagedListError =
            Transformations.switchMap(dataSourceFactory.newsPagedData) {
                dataSourceFactory.newsPagedDataSource.newsListErrorMessage
            }

        newsPagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun refresh() {
        newsListDataSource.invalidate()
    }
}