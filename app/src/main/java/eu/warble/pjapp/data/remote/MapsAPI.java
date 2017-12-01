package eu.warble.pjapp.data.remote;


import com.indoorway.android.map.sdk.IndoorwayMapSdk;

import eu.warble.pjapp.data.MapsDataSource.LoadBuildingsListCallback;

public class MapsAPI {

    public static void loadBuildingsList(LoadBuildingsListCallback callback) {
        IndoorwayMapSdk.getInstance().getBuildingsApi()
                .getBuildings()
                .setOnCompletedListener(callback::onDataLoaded)
                .setOnFailedListener(e -> callback.onDataNotAvailable(e.getMessage()))
                .execute();
    }
}
