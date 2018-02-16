package eu.warble.pjappkotlin.mvp.splash

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.utils.Injection

class SplashActivity : BaseActivity(), SplashContract.View {
    override lateinit var presenter: SplashContract.Presenter

    override val applicationNavigator = ApplicationNavigator(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        initPresenter()
    }

    private fun initPresenter() {
        presenter = SplashPresenter(
                Injection.provideStudentDataRepository(applicationContext),
                this,
                applicationContext
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showConnectionError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.connect_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { presenter.checkUserLogIn() }
                .setActionTextColor(ContextCompat.getColor(this@SplashActivity, R.color.colorAccent))
                .show()
    }

}
