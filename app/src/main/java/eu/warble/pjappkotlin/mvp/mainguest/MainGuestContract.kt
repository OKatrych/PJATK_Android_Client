package eu.warble.pjappkotlin.mvp.mainguest

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface MainGuestContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
    }

    interface Presenter : BasePresenter {

    }
}