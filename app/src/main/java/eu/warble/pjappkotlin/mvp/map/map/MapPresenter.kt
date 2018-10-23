package eu.warble.pjappkotlin.mvp.map.map

import android.content.Context
import com.indoorway.android.common.sdk.listeners.generic.Action0
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.IndoorwayObjectParameters
import com.indoorway.android.common.sdk.model.IndoorwayPosition
import com.indoorway.android.location.sdk.IndoorwayLocationSdk
import com.indoorway.android.location.sdk.model.IndoorwayLocationSdkError
import com.indoorway.android.location.sdk.model.IndoorwayLocationSdkState
import com.indoorway.android.map.sdk.listeners.OnRoomSelectedListener
import eu.warble.pjappkotlin.Application
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.R.string
import eu.warble.pjappkotlin.data.StudentDataRepository

class MapPresenter(
    val view: MapContract.View,
    val studentDataRepository: StudentDataRepository?,
    val appContext: Context
) : MapContract.Presenter {

    private var needToFindLocation = false
    private var isLocationDetermined = false
    private var lastPosition: IndoorwayPosition? = null

    override fun start() {
        //no-op
    }

    override fun loadMap(buildingUUID: String, mapUUID: String, mapName: String) {
        view.showLoadingScreen(true)
        view.loadMap(
            buildingUUID,
            mapUUID,
            onMapLoadCompletedListener = Action1 {
                view.showLoadingScreen(false)
            },
            onMapLoadFailedListener = Action0 {
                view.showLoadingScreen(false)
                view.showError(R.string.error_map_load_failed)
            },
            onRoomSelectedListener = onRoomSelectedListener
        )
    }

    override fun findLocationAndLoadMap() {
        view.showDeterminingLocationScreen(true)
        needToFindLocation = true
        IndoorwayLocationSdk.instance().position().onChange().register(positionChangeListener)
        IndoorwayLocationSdk.instance().state().onError().register(stateErrorListener)
        IndoorwayLocationSdk.instance().state().onChange().register(stateChangeListener)
    }

    private fun onLocationDetermined(position: IndoorwayPosition) {
        isLocationDetermined = true
        view.showDeterminingLocationScreen(false)
        view.showLoadingScreen(true)
        view.loadMap(
            position.buildingUuid,
            position.mapUuid,
            onMapLoadCompletedListener = Action1 {
                view.showLoadingScreen(false)
            },
            onMapLoadFailedListener = Action0 {
                view.showLoadingScreen(false)
                view.showError(R.string.error_map_load_failed)
            },
            onRoomSelectedListener = onRoomSelectedListener
        )
    }

    private val positionChangeListener = Action1<IndoorwayPosition> {
        when {
            !isLocationDetermined -> onLocationDetermined(it)
            !didFlourChanged(it) -> view.printCurrentPosition(it)
            else -> {
                //if flour changed, we need to restart whole Indoorway service due to bugs when changing flour
                restartIndoorwayService()
            }
        }
    }

    private fun restartIndoorwayService() {
        isLocationDetermined = false
        onPause()  // unregister listeners
        Application.restartIndoorway()
        findLocationAndLoadMap()
    }

    private fun didFlourChanged(
        currentPosition: IndoorwayPosition?
    ): Boolean {
        var result = false
        val lastPosition = this.lastPosition
        if (lastPosition != null && currentPosition != null) {
            result = lastPosition.mapUuid != currentPosition.mapUuid
        }
        this.lastPosition = currentPosition
        return result
    }

    private val stateChangeListener = Action1<IndoorwayLocationSdkState> {
        /*when (it.name) {
            "LOCATING_FOREGROUND" -> {
                IndoorwayLocationSdk.instance().position().latest()?.let {
                    this@MapPresenter.onLocationDetermined(it)
                }
            }
        }*/
    }

    private val stateErrorListener = Action1<IndoorwayLocationSdkError> {
        when (it) {
            IndoorwayLocationSdkError.BleNotSupported -> {
                view.showError(appContext.getString(string.ble_not_supported))
            }
            is IndoorwayLocationSdkError.MissingPermission -> {
                view.showError(appContext.getString(string.missing_locationm_permission))
            }
            IndoorwayLocationSdkError.BluetoothDisabled -> {
                view.showMessageWithAction(
                    appContext.getString(string.pls_enable_bluetooth),
                    appContext.getString(R.string.retry)
                ) {
                    findLocationAndLoadMap()
                }
            }
            IndoorwayLocationSdkError.LocationDisabled -> {
                view.showMessageWithAction(
                    appContext.getString(string.pls_enable_location_service),
                    appContext.getString(R.string.retry)) {
                    findLocationAndLoadMap()
                }
            }
            IndoorwayLocationSdkError.UnableToFetchData -> {
                view.showError(
                    appContext.getString(string.indoorway_network_error)
                )
            }
            IndoorwayLocationSdkError.NoRadioMaps -> {
                view.showError(appContext.getString(string.no_beacons_found))
            }
        }
    }

    private val onRoomSelectedListener = object : OnRoomSelectedListener {
        override fun canRoomBeSelected(room: IndoorwayObjectParameters): Boolean {
            // return true if object with given parameters can be selected
            return "inaccessible" != room.type
        }

        override fun onRoomSelected(room: IndoorwayObjectParameters) {
            // called on object selection, check parameters for details
            if (needToFindLocation) {
                view.startNavigationToObject(room.id)
            }
        }

        override fun onSelectionCleared() {
            if (needToFindLocation) {
                view.stopNavigation()
            }
        }
    }

    override fun onAllPermissionsGranted() {
        findLocationAndLoadMap()
    }

    override fun onResume() {
        if (needToFindLocation) {
            IndoorwayLocationSdk.instance().position().onChange().register(positionChangeListener)
            IndoorwayLocationSdk.instance().state().onError().register(stateErrorListener)
            IndoorwayLocationSdk.instance().state().onChange().register(stateChangeListener)
        }
    }

    override fun onPause() {
        IndoorwayLocationSdk.instance().position().onChange().unregister(positionChangeListener)
        IndoorwayLocationSdk.instance().state().onError().unregister(stateErrorListener)
        IndoorwayLocationSdk.instance().state().onChange().unregister(stateChangeListener)
    }
}