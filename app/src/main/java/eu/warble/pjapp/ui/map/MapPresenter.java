package eu.warble.pjapp.ui.map;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.indoorway.android.common.sdk.listeners.generic.Action1;
import com.indoorway.android.fragments.sdk.map.MapFragment;
import com.indoorway.android.location.sdk.IndoorwayLocationSdk;
import com.indoorway.android.location.sdk.model.IndoorwayLocationSdkState;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;

public class MapPresenter extends BaseActivityPresenter<MapActivity>{

    private String buildingUUID;
    private String mapUUID;
    private Action1<IndoorwayLocationSdkState> locationListener;

    MapPresenter(MapActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            buildingUUID = savedInstanceState.getString("buildingUUID");
            mapUUID = savedInstanceState.getString("mapUUID");
        }
        loadMap(activity.mapFragment);
        initLocationListener();
    }

    void loadMap(MapFragment mapFragment) {
        activity.setLoadingState(true);
        mapFragment.getMapView().setOnMapLoadFailedListener(this::onMapLoadFailed);
        mapFragment.getMapView().setOnMapLoadCompletedListener(indoorwayMap -> {
            activity.setLoadingState(false);
            mapFragment.startPositioningService();
        });
        mapFragment.getMapView().load(buildingUUID, mapUUID);
    }

    void setMapParameters(String buildingUUID, String mapUUID){
        this.mapUUID = mapUUID;
        this.buildingUUID = buildingUUID;
    }

    private void initLocationListener(){
        locationListener = indoorwayLocationSdkState -> {
            Log.i("locationListener", indoorwayLocationSdkState.toString());
        };
    }

    private void registerListener(){
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .register(locationListener);
    }

    private void unRegisterListener(){
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .unregister(locationListener);
    }

    void onSaveInstanceState(Bundle outState){
        outState.putString("buildingUUID", buildingUUID);
        outState.putString("mapUUID", mapUUID);
    }

    private void onMapLoadFailed() {
        activity.setLoadingState(false);
        activity.showError(activity.getString(R.string.map_loading_error), true);
    }

    void onResume(){
        registerListener();
    }

    @Override
    protected void onDestroyActivity() {
        unRegisterListener();
        super.onDestroyActivity();
    }
}
