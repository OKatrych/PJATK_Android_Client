package eu.warble.pjappkotlin.mvp


interface BaseView<T> {

    var presenter: T

    fun showError(error: Int)
    fun showError(error: String)
    fun showErrorWithAction(error: Int, actionText: Int, action: () -> Unit)
    fun showErrorWithAction(error: String, actionText: String, action: () -> Unit)

    fun showMessage(message: Int)
    fun showMessage(message: String)
    fun showMessageWithAction(message: Int, actionText: Int, action: () -> Unit)
    fun showMessageWithAction(message: String, actionText: String, action: () -> Unit)
}