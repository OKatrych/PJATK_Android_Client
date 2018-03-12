package eu.warble.pjappkotlin.mvp.map.list

import android.content.Context
import com.indoorway.android.common.sdk.IndoorwaySdk
import com.indoorway.android.common.sdk.listeners.generic.Action1
import com.indoorway.android.common.sdk.model.IndoorwayBuilding
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.NetworkUtil

class MapListPresenter(
        val view: MapListContact.View,
        val context: Context
) : MapListContact.Presenter {

    override fun start() {
        loadBuildings()
    }

    override fun loadBuildings() {
        if (isNetworkAvailable()) {
            view.showLoadingScreen(true)
            IndoorwaySdk.instance()
                    .buildings()
                    .setOnCompletedListener(Action1 {
                        view.showLoadingScreen(false)
                        view.updateMapListItems(it, MapListItemType.BUILDING)
                    })
                    .setOnFailedListener(Action1 {
                        view.showLoadingScreen(false)
                        view.showError(it.message ?: it.toString())
                    })
                    .execute()
        } else {
            view.showError(R.string.connect_error)
        }
    }

    override fun loadMaps(building: IndoorwayBuilding) {
        if (isNetworkAvailable()) {
            view.showLoadingScreen(true)
            IndoorwaySdk.instance()
                    .building()
                    .listMaps(building.uuid)
                    .setOnCompletedListener(Action1 {
                        view.showLoadingScreen(false)
                        view.updateMapListItems(it, MapListItemType.MAP)
                    })
                    .setOnFailedListener(Action1 {
                        view.showLoadingScreen(false)
                        view.showError(it.message ?: it.toString())
                    })
                    .execute()
        } else {
            view.showError(R.string.connect_error)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return NetworkUtil.isNetworkAvailable(context)
    }

}