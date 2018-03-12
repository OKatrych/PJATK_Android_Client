package eu.warble.pjappkotlin.mvp.map

import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface MapContact {
    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun showConnectionError()
        fun updateMapListItems(items: ArrayList<Any>, type: MapListItemType)
        fun showMap(buildingUUID: String, map: IndoorwayObjectId)
        fun showLoadingScreen(show: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadBuildings()
        fun loadMaps()
    }
}