package eu.warble.pjappkotlin.mvp.studentinfo.marks

import android.content.Context
import eu.warble.pjappkotlin.R
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
            //marks card
            val marks = studentData.oceny
            if (marks?.isNotEmpty() == true) {
                val mark = marks[0]
                val type = if (mark.zaliczenie?.toLowerCase() == "egzamin")
                    appContext.getString(R.string.exam)
                else
                    appContext.getString(R.string.pass)
                setMarksSubject1(mark.kodPrzedmiotu)
                setMarksType1(type)
                setMarksMark1(mark.ocena)
                if (marks.size >= 2) {
                    val mark1 = marks[1]
                    val type1 = if (mark1.zaliczenie?.toLowerCase() == "egzamin")
                        appContext.getString(R.string.exam)
                    else
                        appContext.getString(R.string.pass)
                    setMarksSubject2(mark1.kodPrzedmiotu)
                    setMarksType2(type1)
                    setMarksMark2(mark1.ocena)
                }
            }
            //avg marks card
            studentData.studia?.get(0)?.let {
                setAverageYear(it.sredniaRok.toString())
                setAverageStudies(it.sredniaStudia.toString())
            }
        }
    }

}