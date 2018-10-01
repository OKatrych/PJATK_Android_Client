package eu.warble.pjappkotlin

import android.app.Application
import com.indoorway.android.common.sdk.IndoorwaySdk
import com.jakewharton.threetenabp.AndroidThreeTen
import eu.warble.pjappkotlin.utils.Constants

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        initIndoorway()
        AndroidThreeTen.init(this)
    }

    private fun initIndoorway() {
        IndoorwaySdk.initContext(this)
        IndoorwaySdk.configure(Constants.INDOORWAY_TRAFFIC_API_KEY)
    }

    companion object {
        private lateinit var app: eu.warble.pjappkotlin.Application

        fun restartIndoorway() {
            IndoorwaySdk.reset()
            IndoorwaySdk.initContext(app)
            IndoorwaySdk.configure(Constants.INDOORWAY_TRAFFIC_API_KEY)
        }
    }
}