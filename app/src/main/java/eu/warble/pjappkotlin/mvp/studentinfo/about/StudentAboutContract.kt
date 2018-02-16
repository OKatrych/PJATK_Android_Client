package eu.warble.pjappkotlin.mvp.studentinfo.about

import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface StudentAboutContract {

    interface View : BaseView<Presenter> {

        fun setStudentName(studentName: String?)

        fun setStudies(studies: String?)

        fun setYear(year: String?)

        fun setSemester(semester: String?)

        fun setGroups(groups: String?)

        fun setStatus(status: String?)

        fun setAvgMark(avgMark: Float?)

        fun setSpecialization(specialization: String?)

    }

    interface Presenter : BasePresenter {
        //no-op
    }

}