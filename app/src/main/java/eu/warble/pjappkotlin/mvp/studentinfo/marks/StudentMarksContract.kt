package eu.warble.pjappkotlin.mvp.studentinfo.marks

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface StudentMarksContract {

    interface View : BaseView<Presenter> {

        val applicationNavigator: ApplicationNavigator

        fun setMarksSubject1(marksSubject1: String?)

        fun setMarksType1(marksType1: String?)

        fun setMarksMark1(marksMark1: String?)

        fun setMarksSubject2(marksSubject2: String?)

        fun setMarksType2(marksType2: String?)

        fun setMarksMark2(marksMark2: String?)

        fun setAverageYear(averageYear: String?)

        fun setAverageStudies(averageStudies: String?)
    }

    interface Presenter : BasePresenter {
        //no-op
    }

}