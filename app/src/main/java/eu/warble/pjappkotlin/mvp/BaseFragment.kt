package eu.warble.pjappkotlin.mvp

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val TAG: String
    var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }

    fun showError(error: String) {
        if (activity != null) (activity as BaseActivity).showError(error)
    }

    fun showError(error: Int) {
        if (activity != null) (activity as BaseActivity).showError(error)
    }

    fun showErrorWithAction(error: String, actionText: String, action: () -> Unit) {
        if (activity != null) (activity as BaseActivity).showErrorWithAction(error, actionText, action)
    }

    fun showErrorWithAction(error: Int, actionText: Int, action: () -> Unit) {
        if (activity != null) (activity as BaseActivity).showErrorWithAction(error, actionText, action)
    }

    fun showMessage(message: String) {
        if (activity != null) (activity as BaseActivity).showMessage(message)
    }

    fun showMessage(message: Int) {
        if (activity != null) (activity as BaseActivity).showMessage(message)
    }

    fun showMessageWithAction(message: String, actionText: String, action: () -> Unit) {
        if (activity != null) (activity as BaseActivity).showMessageWithAction(message, actionText, action)
    }

    fun showMessageWithAction(message: Int, actionText: Int, action: () -> Unit) {
        if (activity != null) (activity as BaseActivity).showMessageWithAction(message, actionText, action)
    }

    open fun onBack(): Boolean {
        return false
    }
}