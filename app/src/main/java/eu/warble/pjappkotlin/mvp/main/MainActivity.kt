package eu.warble.pjappkotlin.mvp.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.ncapdevi.fragnav.FragNavController
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.studentinfo.StudentInfoFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

class MainActivity : BaseActivity(),
                     FragNavController.TransactionListener,
                     FragNavController.RootFragmentListener,
                     MainContract.View {

    override lateinit var presenter: MainContract.Presenter
    override val applicationNavigator = ApplicationNavigator(this)

    private val INDEX_STUDENT = FragNavController.TAB1
    private val INDEX_SCHEDULE = FragNavController.TAB2
    private val INDEX_MAP = FragNavController.TAB3
    private val INDEX_FTP = FragNavController.TAB4
    private val INDEX_MORE = FragNavController.TAB5

    private var fragNavController: FragNavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val initial = savedInstanceState == null
        val guestModeEnabled = intent.getBooleanExtra("guestMode", false)
        initPresenter(guestModeEnabled)
        if (initial) {
            selectNavigationItem(
                    if (guestModeEnabled)
                        presenter.accessibleTabs[0]
                    else
                        R.id.navigation_student
            )
        }
        initFragNavController(savedInstanceState)
    }

    private fun initPresenter(guestModeEnabled: Boolean) {
        presenter = MainPresenter(
                Injection.provideStudentDataRepository(applicationContext),
                this,
                applicationContext
        ).apply {
            this.guestModeEnabled = guestModeEnabled
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        fragNavController = FragNavController.newBuilder(
                savedInstanceState,
                supportFragmentManager,
                R.id.main_content
        )
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .switchController { id, _ -> bottomNavigationView.selectedItemId = id }
                .build()
        fragNavController?.executePendingTransactions()
        bottomNavigationView.setOnNavigationItemSelectedListener({ item ->
            if (presenter.checkTabAccessible(item.itemId)) {
                fragNavController?.switchTab(getPosition(item.itemId))
                true
            } else {
                applicationNavigator.goToLoginActivity()
                false
            }
        })
        bottomNavigationView.setOnNavigationItemReselectedListener({ fragNavController?.clearStack() })
    }

    private fun selectNavigationItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }

    override fun onFragmentTransaction(p0: Fragment?, p1: FragNavController.TransactionType?) {
        //no-op
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        updateActionBar(index)
    }

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            INDEX_STUDENT -> StudentInfoFragment.newInstance()
            INDEX_SCHEDULE -> ScheduleFragment.newInstance()
            INDEX_MAP -> MapListFragment.newInstance()
            INDEX_FTP -> FtpFragment.newInstance()
            INDEX_MORE -> MoreFragment.newInstance()
            else -> throw IllegalStateException("Need to send an index that we know")
        }
    }

    private fun updateActionBar(index: Int) {
        when (index) {
            INDEX_STUDENT or INDEX_SCHEDULE -> supportActionBar?.elevation = 0f
            else -> supportActionBar?.elevation = 14f
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            fragNavController?.onSaveInstanceState(outState)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.toolbar_logOut -> {
                presenter.logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPosition(fragmentId: Int): Int {
        return when (fragmentId) {
            R.id.navigation_student -> INDEX_STUDENT
            R.id.navigation_schedule -> INDEX_SCHEDULE
            R.id.navigation_maps -> INDEX_MAP
            R.id.navigation_ftp -> INDEX_FTP
            R.id.navigation_more -> INDEX_MORE
            else -> -1
        }
    }

    override fun showApiError(error: String?) {
        AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("PJATK API Error")
                .setMessage(error ?: "Some problems with PJATK API Server")
                .setNeutralButton("Refresh") { dialogInterface, _ ->
                    presenter.checkApiResponseForErrors()
                    dialogInterface.dismiss()
                }
                .show()
    }
}
