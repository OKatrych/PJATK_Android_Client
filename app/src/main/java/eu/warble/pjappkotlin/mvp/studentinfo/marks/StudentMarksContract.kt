package eu.warble.pjappkotlin.mvp.studentinfo.marks

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface StudentMarksContract {

    interface View : BaseView<Presenter> {

        val applicationNavigator: ApplicationNavigator

    }

    interface Presenter : BasePresenter {
        //no-op
    }

}