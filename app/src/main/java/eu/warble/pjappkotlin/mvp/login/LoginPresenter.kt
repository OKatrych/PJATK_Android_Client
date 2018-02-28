package eu.warble.pjappkotlin.mvp.login

import android.content.Context
import android.print.PrintJob
import android.widget.Toast
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.data.remote.PjatkAPI
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.CredentialsManager
import eu.warble.pjappkotlin.utils.NetworkUtil

class LoginPresenter(
        val view: LoginContract.View,
        private val appContext: Context
) : LoginContract.Presenter {

    override fun start() {

    }

    override fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(appContext)
    }

    override fun login(login: String, password: String) {
        if (!isNetworkAvailable()) {
            view.showError(R.string.connect_error)
            return
        }
        val repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(
                        CredentialsManager.Credentials(login, password)
                )
        )
        view.showLoading(true)
        repository.getStudentData(appContext, object : StudentDataSource.LoadStudentDataCallback {
            override fun onDataLoaded(studentData: Student) {
                CredentialsManager.saveCredentials(login, password, appContext)
                view.showLoading(false)
                view.applicationNavigator.goToMainActivity()
            }

            override fun onDataNotAvailable(error: String) {
                view.showLoading(false)
                when (error) {
                    Constants.CREDENTIALS_ERROR -> {
                        StudentDataRepository.destroyInstance()
                        view.showError(R.string.credentials_error)
                    }
                    Constants.CONNECTION_ERROR -> view.showError(R.string.connect_error)
                    else -> view.showError(error)
                }
            }
        })
    }
}