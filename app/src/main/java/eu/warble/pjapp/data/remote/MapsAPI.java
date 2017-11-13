package eu.warble.pjapp.data.remote;


import com.indoorway.android.common.sdk.IndoorwaySdk;

import eu.warble.pjapp.data.MapsDataSource.LoadBuildingsListCallback;

public class MapsAPI {

    public static void loadBuildingsList(LoadBuildingsListCallback callback) {
        IndoorwaySdk.instance()
                .buildings()
                .setOnCompletedListener(callback::onDataLoaded)
                .setOnFailedListener(e -> callback.onDataNotAvailable(e.getMessage()))
                .execute();
    }
}
