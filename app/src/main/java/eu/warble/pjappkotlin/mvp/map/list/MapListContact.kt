package eu.warble.pjappkotlin.mvp.map.list

import com.indoorway.android.common.sdk.model.IndoorwayBuilding
import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView

interface MapListContact {
    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        fun updateMapListItems(items: List<Any>, type: MapListItemType)
        fun showMap(buildingUUID: String, map: IndoorwayObjectId)
        fun showLoadingScreen(show: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadBuildings()
        fun loadMaps(building: IndoorwayBuilding)
    }
}