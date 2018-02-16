package eu.warble.pjappkotlin.utils

import android.app.Activity
import android.content.Context

object CredentialsManager {

    private val PJAPP_PREFS = "Pjapp-prefs"
    private var credentials: Credentials? = null

    fun saveCredentials(
            login: String?,
            password: String?,
            context: Context
    ) {
        this.credentials = Credentials(login, password)
        val preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE)
        preferences.edit()
                .putString("login", login)
                .putString("password", password)
                .apply()
    }

    fun getCredentials(context: Context): Credentials? {
        if (credentials == null) {
            val preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE)
            val login = preferences.getString("login", null)
            val password = preferences.getString("password", null)
            if (login == null || password == null) {
                return null
            }
            credentials = Credentials(login, password)
        }
        return credentials
    }

    data class Credentials(
            var login: String?,
            var password: String?
    )
}