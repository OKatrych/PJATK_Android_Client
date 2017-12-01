package eu.warble.pjapp.data;


import com.indoorway.android.common.sdk.model.IndoorwayBuildingParameters;

import java.util.List;

public interface MapsDataSource {

    interface LoadBuildingsListCallback {
        void onDataLoaded(List<IndoorwayBuildingParameters> indoorwayBuildingsData);
        void onDataNotAvailable(String error);
    }

    void getBuildingsData(LoadBuildingsListCallback callback);
}
