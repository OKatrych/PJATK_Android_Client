package eu.warble.pjappkotlin.mvp.splash

import android.content.Context
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.NetworkUtil


class SplashPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: SplashContract.View,
        private val appContext: Context
) : SplashContract.Presenter {

    override fun start() {
        checkUserLogIn()
    }

    override fun checkUserLogIn() {
        when {
            studentDataRepository == null -> {
                view.applicationNavigator.goToLoginActivity()
                return
            }
            isNetworkAvailable() -> {
                studentDataRepository.refreshStudentData(appContext)
                studentDataRepository.getStudentData(
                        appContext,
                        object : StudentDataSource.LoadStudentDataCallback {
                            override fun onDataLoaded(studentData: Student) {
                                view.applicationNavigator.goToMainActivity()
                            }

                            override fun onDataNotAvailable(error: String) {
                                when (error) {
                                    Constants.CONNECTION_ERROR ->
                                        view.showConnectionError()
                                    Constants.CREDENTIALS_ERROR -> {
                                        StudentDataRepository.destroyInstance()
                                        view.applicationNavigator.goToLoginActivity()
                                    }
                                    else -> view.showError(error)
                                }
                            }
                        })
            }
            else -> view.applicationNavigator.goToMainActivityWithNoInternetMode()
        }

    }

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(appContext)
    }

}