package eu.warble.pjapp.data;


import com.indoorway.android.common.sdk.model.IndoorwayBuildingParameters;

import java.util.List;

import eu.warble.pjapp.data.remote.MapsAPI;

public class MapsDataRepository implements MapsDataSource{

    private static MapsDataRepository INSTANCE = null;

    private List<IndoorwayBuildingParameters> buildingsCachedData;

    private boolean buildingsCacheIsDirty = false;

    // Prevent direct instantiation.
    private MapsDataRepository() {
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     * @return the {@link MapsDataRepository} instance
     */
    public static MapsDataRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MapsDataRepository();
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets data from cache or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadBuildingsListCallback#onDataNotAvailable(String s)} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getBuildingsData(LoadBuildingsListCallback callback) {
        // Respond immediately with cache if available and not dirty
        if (buildingsCachedData != null && !buildingsCacheIsDirty)
            callback.onDataLoaded(buildingsCachedData);
        else
            getBuildingsDataFromRemoteDataSource(callback);
    }

    private void getBuildingsDataFromRemoteDataSource(LoadBuildingsListCallback callback) {
        MapsAPI.loadBuildingsList(new LoadBuildingsListCallback() {
            @Override
            public void onDataLoaded(List<IndoorwayBuildingParameters> indoorwayBuildingsData) {
                refreshCache(indoorwayBuildingsData);
                callback.onDataLoaded(indoorwayBuildingsData);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    public void refreshBuildingsData() {
        buildingsCacheIsDirty = true;
    }

    private void refreshCache(List<IndoorwayBuildingParameters> buildingsData) {
        buildingsCachedData = null;
        buildingsCachedData = buildingsData;
        buildingsCacheIsDirty = false;
    }
}
