package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.NewsDataSource.LoadNewsDataCallback
import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.data.remote.NewsAPI

class NewsDataRepository private constructor(
        private val newsRemoteDataSource: NewsDataSource
) : NewsDataSource {

    private var cachedData: ArrayList<NewsItem>? = null

    private var cacheIsDirty = false

    override fun getNewsData(appContext: Context, callback: LoadNewsDataCallback) {
        val mCachedData = cachedData
        // Respond immediately with cache if available and not dirty
        if (mCachedData != null && !cacheIsDirty) {
            callback.onDataLoaded(mCachedData)
        } else {
            getNewsDataFromRemoteDataSource(appContext, callback)
        }
    }

    private fun getNewsDataFromRemoteDataSource(appContext: Context, callback: LoadNewsDataCallback) {
        newsRemoteDataSource.getNewsData(appContext, object : LoadNewsDataCallback {
            override fun onDataLoaded(newsData: ArrayList<NewsItem>) {
                refreshCache(newsData)
                callback.onDataLoaded(newsData)
            }

            override fun onDataNotAvailable(error: String) {
                callback.onDataNotAvailable(error)
            }
        })
    }

    fun refreshNewsData() {
        cacheIsDirty = true
    }

    private fun refreshCache(newsData: ArrayList<NewsItem>) {
        cachedData = null
        cachedData = newsData
        cacheIsDirty = false
    }

    companion object {
        private var INSTANCE: NewsDataRepository? = null

        @JvmStatic
        fun getInstance(newsRemoteDataSource: NewsDataSource): NewsDataRepository {
            return INSTANCE ?: NewsDataRepository(newsRemoteDataSource).apply {
                INSTANCE = this
            }
        }

        @JvmStatic
        fun destroyInstance() {
            NewsAPI.destroyInstance()
            INSTANCE = null
        }
    }

}