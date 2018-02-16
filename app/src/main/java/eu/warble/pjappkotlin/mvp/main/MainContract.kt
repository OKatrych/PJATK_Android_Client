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
        var guestModeEnabled: Boolean
        val accessibleTabs: Array<Int>
        fun checkTabAccessible(fragmentId: Int): Boolean
        fun checkApiResponseForErrors()
        fun logOut()
    }
}