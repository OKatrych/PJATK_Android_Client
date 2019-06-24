package eu.warble.pjappkotlin.mvp.studentinfo.about

import android.content.Context
import android.text.TextUtils
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student

const val SPECIALIZATION_SUM = "Sieci urządzeń mobilnych"

class StudentAboutPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: StudentAboutContract.View,
        private val appContext: Context
) : StudentAboutContract.Presenter {

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
        view.setStudentName(studentData.imie)
        if (studentData.studia?.isNotEmpty() == true) {
            view.setStudies(studentData.studia[0].kierunek?.nazwaKierunekAng)
            view.setYear(studentData.studia[0].rokStudiow.toString())
            view.setSemester(studentData.studia[0].semestrStudiow.toString())
            view.setAvgMark(studentData.studia[0].sredniaStudia?.toFloat())
            view.setSpecialization(studentData.studia[0].specjalizacja)
            setUpHiddenFeatures(studentData.studia[0].specjalizacja)
        }
        view.setGroups(TextUtils.join(", ", studentData.grupy))
        view.setStatus(studentData.status)
    }

    /*
     * Show some special features (such as SUM specialization card)
     */
    private fun setUpHiddenFeatures(specialization: String?) {

        when (specialization) {
            SPECIALIZATION_SUM -> {
                view.showHiddenSUMCard()
            }
        }

    }

}