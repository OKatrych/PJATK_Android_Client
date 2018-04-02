package eu.warble.pjappkotlin.mvp.splash

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface SplashContract {

    interface View : BaseView<Presenter> {
        fun showConnectionError()
        fun goToLoginActivity()
        fun goToMainActivity()
        fun goToMainActivityWithNoInternetMode()
    }

    interface Presenter : BasePresenter {
        fun isNetworkAvailable(): Boolean
        fun checkUserLogIn()
    }

}