package eu.warble.pjappkotlin.mvp.ftp

import eu.warble.getter.model.GetterFile
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment

class ScheduleFragment : BaseFragment(), FtpContact.View {

    override val TAG: String = "fragment_ftp"

    override lateinit var presenter: FtpContact.Presenter

    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }

    override fun showLoadingScreen(show: Boolean) {

    }

    override fun updateDirectoriesList(newData: List<GetterFile>) {

    }

    companion object {
        fun newInstance() = ScheduleFragment()
    }
}