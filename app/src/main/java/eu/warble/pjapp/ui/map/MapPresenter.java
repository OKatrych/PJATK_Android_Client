package eu.warble.pjapp.ui.map;


import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;

public class MapPresenter extends BaseActivityPresenter<MapActivity> {

    private String buildingUUID;
    private String mapUUID;

    MapPresenter(MapActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        activity.setLoadingState(true);
        if (savedInstanceState != null) {
            buildingUUID = savedInstanceState.getString("buildingUUID");
            mapUUID = savedInstanceState.getString("mapUUID");
        }
        if (buildingUUID != null && mapUUID != null)
            activity.loadMap(buildingUUID, mapUUID);
        else
            onMapLoadFailed();
    }

    void setMapParameters(String mapUUID, String buildingUUID){
        this.mapUUID = mapUUID;
        this.buildingUUID = buildingUUID;
    }

    void onMapLoadCompleted() {
        activity.setLoadingState(false);
    }

    void onMapLoadFailed() {
        activity.showError(activity.getString(R.string.map_loading_error), true);
    }

    void onSaveInstanceState(Bundle outState){
        outState.putString("buildingUUID", buildingUUID);
        outState.putString("mapUUID", mapUUID);
    }
}
