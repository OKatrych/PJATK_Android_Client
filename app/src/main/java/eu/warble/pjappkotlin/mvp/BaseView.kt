package eu.warble.pjappkotlin.mvp


interface BaseView<T> {

    var presenter: T

    fun showError(error: String)
    fun showError(error: Int)

}