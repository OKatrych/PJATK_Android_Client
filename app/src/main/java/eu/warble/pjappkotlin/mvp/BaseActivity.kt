package eu.warble.pjappkotlin.mvp

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.setBackgroundColor
import eu.warble.pjappkotlin.utils.setTextColor


abstract class BaseActivity : AppCompatActivity() {

    fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.white))
                .show()
    }

    fun showError(error: Int) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.white))
                .show()
    }

    fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show()
    }
}