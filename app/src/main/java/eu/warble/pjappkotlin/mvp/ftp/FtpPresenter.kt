package eu.warble.pjappkotlin.mvp.ftp

import android.content.Context
import android.util.Log
import eu.warble.getter.DownloadCallback
import eu.warble.getter.Getter
import eu.warble.getter.LoadDirectoryCallback
import eu.warble.getter.model.Credentials
import eu.warble.getter.model.GetterFile
import eu.warble.getter.utils.Converter
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.CredentialsManager
import eu.warble.pjappkotlin.utils.FileManager
import eu.warble.pjappkotlin.utils.NetworkUtil
import eu.warble.pjappkotlin.utils.NotificationManager

class FtpPresenter(
        private val credentials: CredentialsManager.Credentials?,
        val view: FtpContact.View,
        private val appContext: Context
) : FtpContact.Presenter {

    override fun start() {
        view.showLoadingScreen(true)
        credentials?.let {
            val login = credentials.login
            val password = credentials.password
            if (login != null && password != null) {
                Getter.init(
                        Constants.SFTP_SERVER_URL,
                        Credentials(login, password),
                        onSuccess = {
                            loadDirectory("/")
                        },
                        onFailure = {
                            view.showErrorWithAction(R.string.connect_error, R.string.retry, {
                                start()
                            })
                            Log.e("Getter", it.message)
                        }
                )
            } else {
                view.showError("Credentials are null")
            }
        }
    }

    override fun loadDirectory(path: String) {
        if (NetworkUtil.isNetworkAvailable(appContext)) {
            view.showLoadingScreen(true)
            Getter.loadDirectory(path, object : LoadDirectoryCallback {
                override fun onDirectoryLoaded(getterFiles: List<GetterFile>) {
                    view.updateDirectoriesList(ArrayList(getterFiles))
                    view.showLoadingScreen(false)
                }

                override fun onError(error: String) {
                    view.showLoadingScreen(false)
                    view.showError(Constants.UNKNOWN_ERROR)
                    Log.e("Getter", error)
                }
            })
        } else {
            view.showLoadingScreen(false)
            view.showError(R.string.connect_error)
        }
    }

    override fun downloadFile(file: GetterFile) {
        if (NetworkUtil.isNetworkAvailable(appContext)) {
            val notification = NotificationManager.createDownloadFileNotification(
                    appContext,
                    appContext.getString(R.string.download),
                    "${appContext.getString(R.string.download_progress)} ${file.name}"
            )

            Getter.download(file, object : DownloadCallback {

                override fun onProgressChanged(percent: Int) {
                    notification.setProgress(100, percent, false)
                    NotificationManager.updateDownloadNotification(appContext, notification)
                }

                override fun onFinish(destination: String) {
                    if (FileManager.fileExists(destination)) {
                        NotificationManager.downloadNotificationSuccess(
                                appContext,
                                notification,
                                destination,
                                file.name
                        )
                    } else {
                        NotificationManager.downloadNotificationFailure(
                                appContext,
                                notification,
                                file.name
                        )
                    }
                }

                override fun onError() {
                    NotificationManager.downloadNotificationFailure(
                            appContext,
                            notification,
                            file.name
                    )
                }

            })
        } else {
            view.showLoadingScreen(false)
            view.showError(R.string.connect_error)
        }
    }

    override fun onBack(): Boolean {
        if (Getter.currentPath != "/") {
            loadDirectory("..")
            return true
        }
        return false
    }
}