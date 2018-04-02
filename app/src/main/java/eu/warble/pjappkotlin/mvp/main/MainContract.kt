package eu.warble.pjappkotlin.mvp.main

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface MainContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun showApiError(error: String?)
    }

    interface Presenter : BasePresenter {
        fun checkApiResponseForErrors()
        fun logOut()
    }
}