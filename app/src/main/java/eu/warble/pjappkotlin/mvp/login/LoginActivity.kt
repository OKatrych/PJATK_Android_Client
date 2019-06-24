package eu.warble.pjappkotlin.mvp.login

import android.os.Bundle
import android.view.View
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.net.Uri
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.Tools


class LoginActivity : BaseActivity(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter
    override val applicationNavigator: ApplicationNavigator = ApplicationNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeWithoutActionBar)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(
            this,
            applicationContext
        )
        initViews()
    }

    private fun initViews() {
        loginBtn.setOnClickListener {
            presenter.login(login.text.toString(), password.text.toString())
        }

        loginGuestBtn.setOnClickListener {
            if (presenter.isNetworkAvailable())
                applicationNavigator.goToMainActivityWithGuestMode()
            else
                showError(R.string.connect_error)
        }

        val privacyPolicyText = privacy_policy
        privacyPolicyText.text = Tools.fromHtml(getString(R.string.privacy_policy_message))
        privacyPolicyText.setOnClickListener {
            openPrivacyPolicyPage()
        }
    }

    private fun openPrivacyPolicyPage() {
        applicationNavigator.openWebPage(Constants.PRIVACY_POLICY_URL)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showLoading(show: Boolean) {
        loading_screen.visibility = if (show) View.VISIBLE else View.GONE
        loginGuestBtn.isEnabled = !show
        loginBtn.isEnabled = !show
    }
}
