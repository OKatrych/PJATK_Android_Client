package eu.warble.pjappkotlin.mvp.ftp

import android.content.Context
import android.util.Log
import eu.warble.getter.DownloadCallback
import eu.warble.getter.Getter
import eu.warble.getter.LoadDirectoryCallback
import eu.warble.getter.model.Credentials
import eu.warble.getter.model.GetterFile
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.NetworkUtil

class FtpPresenter(
        val credentials: Credentials,
        val view: FtpContact.View,
        private val appContext: Context
) : FtpContact.Presenter {

    override fun start() {
        Getter.init(
                Constants.SFTP_SERVER_URL,
                credentials,
                onSuccess = {
                    loadDirectory("/")
                },
                onFailure = {
                    view.showError(R.string.connect_error)
                    Log.e("Getter", it.message)
                }
        )
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
            Getter.download(file, object : DownloadCallback {
                override fun onEnd() {
                }

                override fun onError() {
                }

                override fun onProgressChanged(percent: Int) {
                }
            })
        } else {
            view.showLoadingScreen(false)
            view.showError(R.string.connect_error)
        }
    }
}