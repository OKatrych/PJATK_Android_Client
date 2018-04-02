package eu.warble.pjappkotlin.data.remote

import android.content.Context
import android.util.Log
import eu.warble.pjappkotlin.data.NewsDataSource
import eu.warble.pjappkotlin.utils.AppExecutors
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.Converter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit


class NewsAPI: NewsDataSource {

    private var httpClient: OkHttpClient? = null

    init {
        httpClient = OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .build()
    }

    override fun getNewsData(appContext: Context, callback: NewsDataSource.LoadNewsDataCallback) {
        AppExecutors.NETWORK().execute {
            val url = Constants.API_NEWS_JSON
            makeRequest(url, object : ResponseCallback {
                override fun onResponseLoaded(response: Response) {
                    val body = response.body()
                    if (body != null) {
                        val json = Converter.responseToString(body)
                        val newsData = Converter.jsonStringToNewsList(json)
                        if (newsData != null) {
                            AppExecutors.MAIN().execute { callback.onDataLoaded(newsData) }
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

    private fun makeRequest(url: String, responseCallback: ResponseCallback) {
        if (httpClient == null) {
            Log.e("NewsApi", "httpClient is not initialized")
            responseCallback.onError(Constants.UNKNOWN_ERROR)
            return
        }
        val request = Request.Builder().url(url).build()
        try {
            httpClient?.newCall(request)?.execute().use {
                when (it?.code()) {
                    OK -> responseCallback.onResponseLoaded(it)
                    else -> {
                        Log.e("NewsApi", "Error response code = ${it?.code()}")
                        responseCallback.onError(Constants.UNKNOWN_ERROR)
                    }
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
        private var INSTANCE: NewsAPI? = null
        private val OK = 200

        @JvmStatic
        fun getInstance(): NewsAPI {
            return INSTANCE ?: NewsAPI().apply { INSTANCE = this }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}