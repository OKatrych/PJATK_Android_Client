package eu.warble.pjappkotlin.mvp.news

import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface NewsContract {
    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun showNewsItems(items: ArrayList<NewsItem>)
        fun showConnectionError()
    }

    interface Presenter : BasePresenter {
        fun isNetworkAvailable(): Boolean

    }
}