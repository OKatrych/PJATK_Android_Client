package eu.warble.pjappkotlin.mvp.main

import android.content.Context
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.CredentialsManager
import eu.warble.pjappkotlin.utils.Tools


class MainPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: MainContract.View,
        private val appContext: Context
) : MainContract.Presenter {

    override var guestModeEnabled: Boolean = false

    /**
     * Guest mode accessible tabs
     */
    override val accessibleTabs: Array<Int> = arrayOf(
            R.id.navigation_maps,
            R.id.navigation_more
    )

    override fun start() {
        checkApiResponseForErrors()
    }

    override fun checkTabAccessible(fragmentId: Int): Boolean {
        return !guestModeEnabled || accessibleTabs.contains(fragmentId)
    }

    override fun checkApiResponseForErrors() {
        if (!guestModeEnabled) {
            studentDataRepository?.getStudentData(appContext, object : StudentDataSource.LoadStudentDataCallback {
                override fun onDataLoaded(studentData: Student) {
                    if (!Tools.checkApiResponseForErrors(studentData)) {
                        view.showApiError(null)
                    }
                }

                override fun onDataNotAvailable(error: String) {
                    view.showApiError(error)
                }
            })
        }
    }

    override fun logOut() {
        studentDataRepository?.deleteAllLocalStudentData(appContext)
        StudentDataRepository.destroyInstance()
        CredentialsManager.saveCredentials(login = null, password = null, context = appContext)
        view.applicationNavigator.goToLoginActivity()
    }
}