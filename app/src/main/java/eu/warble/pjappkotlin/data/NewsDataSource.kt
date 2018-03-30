package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.model.NewsItem

interface NewsDataSource {

    interface LoadNewsDataCallback {
        fun onDataLoaded(newsData: ArrayList<NewsItem>)
        fun onDataNotAvailable(error: String)
    }

    fun getNewsData(
            appContext: Context,
            callback: LoadNewsDataCallback
    )
}