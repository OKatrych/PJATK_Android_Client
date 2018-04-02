package eu.warble.pjappkotlin.mvp.news

import android.content.Context
import eu.warble.pjappkotlin.data.NewsDataRepository
import eu.warble.pjappkotlin.data.NewsDataSource
import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.utils.NetworkUtil


class NewsPresenter(
        private val newsDataRepository: NewsDataRepository?,
        val view: NewsContract.View,
        private val appContext: Context
) : NewsContract.Presenter {

    override fun start() {
        loadNewsData()
    }

    override fun loadNewsData() {
        if (isNetworkAvailable()) {
            view.showLoadingScreen(true)
            newsDataRepository?.getNewsData(appContext, object : NewsDataSource.LoadNewsDataCallback {
                override fun onDataLoaded(newsData: ArrayList<NewsItem>) {
                    view.showLoadingScreen(false)
                    view.showNewsItems(newsData)
                }

                override fun onDataNotAvailable(error: String) {
                    view.showLoadingScreen(false)
                    view.showError(error)
                }
            })
        } else {
            view.showConnectionError {
                //when press "retry", try to load news data again
                loadNewsData()
            }
        }
    }

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(appContext)
    }

}