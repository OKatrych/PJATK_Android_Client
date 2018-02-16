package eu.warble.pjappkotlin.mvp.studentinfo.marks

import android.content.Context
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student

class StudentMarksPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: StudentMarksContract.View,
        private val appContext: Context
) : StudentMarksContract.Presenter {

    override fun start() {
        studentDataRepository?.getStudentData(appContext, object : StudentDataSource.LoadStudentDataCallback {
            override fun onDataLoaded(studentData: Student) {
                fillViews(studentData)
            }

            override fun onDataNotAvailable(error: String) {
                view.showError(error)
            }
        })
    }

    private fun fillViews(studentData: Student) {
        with(view) {

        }
    }

}