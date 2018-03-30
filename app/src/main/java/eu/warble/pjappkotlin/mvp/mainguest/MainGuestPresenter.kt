package eu.warble.pjappkotlin.mvp.mainguest

import android.content.Context
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.CredentialsManager
import eu.warble.pjappkotlin.utils.Tools


class MainGuestPresenter(
        val view: MainGuestContract.View,
        private val appContext: Context
) : MainGuestContract.Presenter {

    override fun start() {

    }
}