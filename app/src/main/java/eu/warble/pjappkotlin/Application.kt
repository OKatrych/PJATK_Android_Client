package eu.warble.pjappkotlin

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import eu.warble.pjappkotlin.utils.Constants

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        initIndoorway()
        AndroidThreeTen.init(this)
    }

    private fun initIndoorway(){
        /*IndoorwaySdk.initContext(this)
        IndoorwaySdk.configure(Constants.INDOORWAY_TRAFFIC_API_KEY)*/
    }
}