package eu.warble.pjappkotlin.mvp.ftp

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.getter.model.GetterFile
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.fragment_ftp.view.directories_list
import kotlinx.android.synthetic.main.fragment_ftp.view.loading_screen

class FtpFragment : BaseFragment(), FtpContact.View {

    override val TAG: String = "fragment_ftp"
    override lateinit var presenter: FtpContact.Presenter
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }

    private lateinit var loadingScreen: View
    private lateinit var directoriesList: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ftp, container, false)
        initViews(view)
        initPresenter()
        presenter.start()
        return view
    }

    private fun initViews(view: View) {
        loadingScreen = view.loading_screen
        directoriesList = view.directories_list
        directoriesList.layoutManager = LinearLayoutManager(directoriesList.context)
        directoriesList.adapter = FtpListAdapter(emptyList())
    }

    override fun showLoadingScreen(show: Boolean) {
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun updateDirectoriesList(newData: ArrayList<GetterFile>) {
        (directoriesList.adapter as FtpListAdapter).updateList(newData)
    }

    private fun initPresenter() {
        presenter = FtpPresenter(
                Injection.provideCredentials(mContext as Context),
                this,
                mContext as Context
        )
    }

    companion object {
        fun newInstance() = FtpFragment()
    }
}