package eu.warble.pjappkotlin.data.remote

import android.content.Context
import android.util.Log
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.utils.AppExecutors
import eu.warble.pjappkotlin.utils.Converter
import eu.warble.pjappkotlin.utils.CredentialsManager
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class PjatkAPI private constructor(credentials: CredentialsManager.Credentials) : StudentDataSource {

    private var httpClient: OkHttpClient? = null

    init {
        val login = credentials.login
        val password = credentials.password
        if (login != null && password != null) {
            httpClient = OkHttpClient.Builder()
                    .authenticator(NTLMAuthenticator(login, password))
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .build()
        }
    }

    override fun getStudentData(appContext: Context, callback: StudentDataSource.LoadStudentDataCallback) {
        if (httpClient == null) {
            Log.e("PjatkApi", "httpClient is not initialized")
            callback.onDataNotAvailable(Constants.UNKNOWN_ERROR)
            return
        }
        val request = Request.Builder()
                .url(Constants.API_STUDENT_PERSONAL_DATA_JSON)
                .build()
        AppExecutors.NETWORK().execute {
            try {
                httpClient?.newCall(request)?.execute().use {
                    when (it?.code()) {
                        OK -> {
                            val body = it.body()
                            if (body != null) {
                                val studentData = Converter.jsonStringToStudentData(
                                        Converter.responseToJsonString(body)
                                )
                                if (studentData != null)
                                    AppExecutors.MAIN().execute { callback.onDataLoaded(studentData) }
                                else
                                    AppExecutors.MAIN().execute {
                                        callback.onDataNotAvailable(Constants.UNKNOWN_ERROR)
                                    }
                            } else {
                                AppExecutors.MAIN().execute {
                                    callback.onDataNotAvailable(Constants.CONNECTION_ERROR)
                                }
                            }
                        }
                        WRONG_CREDENTIALS -> AppExecutors.MAIN().execute {
                            callback.onDataNotAvailable(Constants.CREDENTIALS_ERROR)
                        }
                        else -> AppExecutors.MAIN().execute {
                            callback.onDataNotAvailable(Constants.UNKNOWN_ERROR)
                        }
                    }
                }
            } catch (ex: Exception) {
                AppExecutors.MAIN().execute {
                    callback.onDataNotAvailable(Constants.CONNECTION_ERROR)
                }
            }
        }
    }

    companion object {
        private var INSTANCE: PjatkAPI? = null
        private val WRONG_CREDENTIALS = 401
        private val OK = 200

        @JvmStatic
        fun getInstance(credentials: CredentialsManager.Credentials): StudentDataSource {
            return INSTANCE ?: PjatkAPI(credentials).apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}