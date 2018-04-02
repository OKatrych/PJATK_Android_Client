package eu.warble.pjappkotlin.mvp.mainguest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.ncapdevi.fragnav.FragNavController
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.mvp.ftp.FtpFragment
import eu.warble.pjappkotlin.mvp.map.list.MapListFragment
import eu.warble.pjappkotlin.mvp.news.NewsFragment
import eu.warble.pjappkotlin.mvp.schedule.ScheduleFragment
import eu.warble.pjappkotlin.mvp.studentinfo.StudentInfoFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

class MainGuestActivity : BaseActivity(),
                          FragNavController.TransactionListener,
                          MainGuestContract.View {

    override lateinit var presenter: MainGuestContract.Presenter
    override val applicationNavigator = ApplicationNavigator(this)

    private val INDEX_NEWS = FragNavController.TAB1
    private val INDEX_MAP = FragNavController.TAB2
    private var fragNavController: FragNavController? = null

    private val fragments: List<Fragment> = mutableListOf(
            NewsFragment.newInstance(),
            MapListFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_guest)
        val initial = savedInstanceState == null
        initPresenter()

        if (initial) {
            selectNavigationItem(R.id.navigation_news)
        }
        initFragNavController(savedInstanceState)
    }

    private fun initPresenter() {
        presenter = MainGuestPresenter(
                this,
                applicationContext
        )
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
                .rootFragments(fragments)
                .switchController { id, _ -> bottomNavigationView.selectedItemId = id }
                .build()
        fragNavController?.executePendingTransactions()
        bottomNavigationView.setOnNavigationItemSelectedListener({ item ->
            fragNavController?.switchTab(getPosition(item.itemId))
            true
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
        supportActionBar?.elevation = 14f
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            fragNavController?.onSaveInstanceState(outState)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater?.inflate(R.menu.toolbar_guest, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.toolbar_logIn -> {
                applicationNavigator.goToLoginActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPosition(fragmentId: Int): Int {
        return when (fragmentId) {
            R.id.navigation_news -> INDEX_NEWS
            R.id.navigation_maps -> INDEX_MAP
            else -> -1
        }
    }

    override fun onBackPressed() {
        val fragmentsList = supportFragmentManager.fragments
        var handled = false
        fragmentsList.forEach {
            if (it is BaseFragment) {
                handled = it.onBack()
                if (handled) {
                    return@forEach
                }
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }
}
