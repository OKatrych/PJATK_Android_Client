package eu.warble.pjapp.data;


import com.indoorway.android.common.sdk.model.IndoorwayBuilding;

import java.util.List;

public interface MapsDataSource {

    interface LoadBuildingsListCallback {
        void onDataLoaded(List<IndoorwayBuilding> indoorwayBuildingsData);
        void onDataNotAvailable(String error);
    }

    void getBuildingsData(LoadBuildingsListCallback callback);
}
