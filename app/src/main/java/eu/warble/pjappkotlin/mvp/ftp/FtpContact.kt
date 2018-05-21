package eu.warble.pjappkotlin.mvp.ftp

import eu.warble.getter.model.GetterFile
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface FtpContact {
    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun showLoadingScreen(show: Boolean)
        fun updateDirectoriesList(newData: ArrayList<GetterFile>)
    }

    interface Presenter : BasePresenter {
        fun loadDirectory(path: String)
        fun reload()
        fun downloadFile(file: GetterFile)
        fun onBack(): Boolean
    }
}