package com.loblaw.challenge

/**
 * A singleton class containing all the constants used in the app.
 * All string constants that might require translation should not be added here they
 * should be part of strings.xml file.
 */
object Constants {

    const val BASE_URL = "https://www.reddit.com/"
    const val RECYCLER_VIEW_PAGE_SIZE = 30
    const val ERROR = "error"
    const val UNAUTHORIZED = "unauthorized"
    const val NETWORK_ERROR = "networkError"

    //Fragment Tags
    const val NEWS_DETAIL_FRAGMENT = "newsDetailFragment"
    const val NEWS_LIST_FRAGMENT = "newsListFragment"

    //Fragment Parameters
    const val CHILD_NEWS_DATA = "childNewsData"
}