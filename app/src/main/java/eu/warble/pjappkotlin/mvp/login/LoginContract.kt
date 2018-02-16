package eu.warble.pjappkotlin.mvp.login

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun showLoading(show: Boolean)
    }

    interface Presenter : BasePresenter {
        fun isNetworkAvailable(): Boolean
        fun login(login: String, password: String)
    }

}