package eu.warble.pjappkotlin

import android.app.Application
import eu.warble.pjappkotlin.utils.Constants

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        initIndoorway()
    }

    private fun initIndoorway(){
        /*IndoorwaySdk.initContext(this)
        IndoorwaySdk.configure(Constants.INDOORWAY_TRAFFIC_API_KEY)*/
    }
}