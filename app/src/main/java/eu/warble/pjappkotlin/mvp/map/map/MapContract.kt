package eu.warble.pjappkotlin.mvp.map.map

import com.indoorway.android.common.sdk.listeners.generic.Action0
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.IndoorwayMap
import com.indoorway.android.common.sdk.model.IndoorwayPosition
import com.indoorway.android.map.sdk.listeners.OnRoomSelectedListener
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface MapContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun loadMap(buildingUUID: String,
                    mapUUID: String,
                    onMapLoadCompletedListener: Action1<IndoorwayMap>,
                    onMapLoadFailedListener: Action0,
                    onRoomSelectedListener: OnRoomSelectedListener
        )

        fun printCurrentPosition(position: IndoorwayPosition)
        fun showLoadingScreen(show: Boolean)
        fun showDeterminingLocationScreen(show: Boolean)
        fun startNavigationToObject(objectId: String)
        fun stopNavigation()
        fun findLocation()
    }

    interface Presenter : BasePresenter {
        fun findLocationAndLoadMap()
        fun onAllPermissionsGranted()
        fun onResume()
        fun onPause()
        fun loadMap(buildingUUID: String, mapUUID: String, mapName: String)
    }
}