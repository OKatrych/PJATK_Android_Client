package eu.warble.pjappkotlin.data.remote

import android.content.Context
import android.util.Log
import eu.warble.pjappkotlin.data.ScheduleDataSource
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.utils.AppExecutors
import eu.warble.pjappkotlin.utils.Converter
import eu.warble.pjappkotlin.utils.CredentialsManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.threeten.bp.LocalDate
import java.util.concurrent.TimeUnit

class PjatkAPI private constructor(credentials: CredentialsManager.Credentials) : StudentDataSource, ScheduleDataSource {

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
        AppExecutors.NETWORK().execute {
            makeRequest(Constants.API_STUDENT_PERSONAL_DATA_JSON, object : ResponseCallback {
                override fun onResponseLoaded(response: Response) {
                    val body = response.body()
                    if (body != null) {
                        val studentData = Converter.jsonStringToStudentData(
                                Converter.responseToString(body)
                        )
                        if (studentData != null) {
                            AppExecutors.MAIN().execute { callback.onDataLoaded(studentData) }
                        } else {
                            onError(Constants.UNKNOWN_ERROR)
                        }
                    } else {
                        onError(Constants.UNKNOWN_ERROR)
                    }
                }

                override fun onError(error: String) {
                    AppExecutors.MAIN().execute { callback.onDataNotAvailable(error) }
                }
            })
        }
    }

    override fun getScheduleData(appContext: Context, from: LocalDate, to: LocalDate, callback: ScheduleDataSource.LoadScheduleDataCallback) {
        AppExecutors.NETWORK().execute {
            makeRequest("${Constants.API_STUDENT_SCHEDULE}&${from.format(Constants.API_DATE_FORMAT)}&${to.format(Constants.API_DATE_FORMAT)}",
                    object : ResponseCallback {
                        override fun onResponseLoaded(response: Response) {
                            val body = response.body()
                            if (body != null) {
                                val scheduleData = Converter.jsonStringToScheduleList(
                                        Converter.responseToString(body)
                                )
                                if (scheduleData != null) {
                                    AppExecutors.MAIN().execute { callback.onDataLoaded(scheduleData) }
                                } else {
                                    onError(Constants.UNKNOWN_ERROR)
                                }
                            } else {
                                onError(Constants.UNKNOWN_ERROR)
                            }
                        }

                        override fun onError(error: String) {
                            AppExecutors.MAIN().execute { callback.onDataNotAvailable(error) }
                        }
                    }
            )
        }
    }

    private fun makeRequest(url: String, responseCallback: ResponseCallback) {
        if (httpClient == null) {
            Log.e("PjatkApi", "httpClient is not initialized")
            responseCallback.onError(Constants.UNKNOWN_ERROR)
            return
        }
        val request = Request.Builder().url(url).build()
        try {
            httpClient?.newCall(request)?.execute().use {
                when (it?.code()) {
                    OK -> responseCallback.onResponseLoaded(it)
                    WRONG_CREDENTIALS -> responseCallback.onError(Constants.CREDENTIALS_ERROR)
                    else -> responseCallback.onError(Constants.UNKNOWN_ERROR)
                }
            }
        } catch (ex: Exception) {
            responseCallback.onError(Constants.CONNECTION_ERROR)
        }
    }

    private interface ResponseCallback {
        fun onResponseLoaded(response: Response)
        fun onError(error: String)
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