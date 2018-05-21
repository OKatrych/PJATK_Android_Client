package eu.warble.pjappkotlin.mvp.ftp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import android.widget.Toast
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class FtpFragment : BaseFragment(), FtpContact.View, SearchView.OnQueryTextListener {

    override val TAG: String = "fragment_ftp"
    override lateinit var presenter: FtpContact.Presenter
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }
    private lateinit var loadingScreen: View
    private lateinit var directoriesList: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ftp, container, false)
        setHasOptionsMenu(true)
        initViews(view)
        initPresenter()
        presenter.start()
        return view
    }

    private fun initViews(view: View) {
        loadingScreen = view.loading_screen
        directoriesList = view.directories_list
        directoriesList.layoutManager = LinearLayoutManager(directoriesList.context)
        directoriesList.adapter = FtpListAdapter(
                emptyList(),
                onItemClick = { file: GetterFile ->
                    if (file.isDirectory()) {
                        presenter.loadDirectory(file.path)
                        if (!searchView.isIconified)
                            searchItem.collapseActionView()
                    } else {
                        downloadFileWithPermissionCheck(file)
                    }
                }
        )
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

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun downloadFile(file: GetterFile) {
        presenter.downloadFile(file)
        showMessage(getString(R.string.download_start))
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForStorage(request: PermissionRequest) {
        AlertDialog.Builder(mContext as Context)
                .setMessage(R.string.permission_rationale_storage_message)
                .setPositiveButton(R.string.button_allow, { _, _ -> request.proceed() })
                .setNegativeButton(R.string.button_deny, { _, _ -> request.cancel() })
                .setCancelable(false)
                .show()
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDeniedForStorage() {
        Toast.makeText(activity, R.string.permissions_declined, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar, menu)
        searchItem = menu.findItem(R.id.action_search).apply {
            isEnabled = true
            isVisible = true
        }
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if (loadingScreen.visibility == View.GONE)
            (directoriesList.adapter as FtpListAdapter).filter.filter(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onBack(): Boolean {
        return presenter.onBack()
    }

    companion object {
        fun newInstance() = FtpFragment()
    }
}