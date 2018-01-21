package eu.warble.pjapp.ui.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.indoorway.android.common.sdk.model.IndoorwayBuilding;

import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.MapsDataRepository;
import eu.warble.pjapp.data.MapsDataSource;
import eu.warble.pjapp.data.model.MapListItem;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.NetworkHelper;

public class MapListPresenter extends BaseFragmentPresenter<MapListFragment> {


    MapListPresenter(MapListFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        loadMaps();
    }

    private void loadMaps(){
        fragment.setLoadingState(true);
        if (!NetworkHelper.isNetworkAvailable(fragment.context)){
            MapsDataRepository.destroyInstance();
            fragment.setLoadingState(false);
            fragment.showError(fragment.getString(R.string.connect_error));
            return;
        }

        MapsDataRepository repository = MapsDataRepository.getInstance();
        repository.getBuildingsData(new MapsDataSource.LoadBuildingsListCallback() {
            @Override
            public void onDataLoaded(List<IndoorwayBuilding> indoorwayBuildingsData) {
                fragment.setLoadingState(false);
                fragment.updateList(indoorwayBuildingsData);
            }

            @Override
            public void onDataNotAvailable(String error) {
                Log.e("MapListPresenter", error);
                fragment.setLoadingState(false);
                fragment.showError(error);
            }
        });
    }

    void onMapItemClicked(MapListItem item) {
        Intent intent = new Intent(fragment.getActivity(), MapActivity.class);
        intent.putExtra("mapUUID", item.getMapUUID());
        intent.putExtra("buildingUUID", item.getBuildingUUID());
        String mapName = String.format("%s %s: %s", fragment.getString(R.string.building), item.getBuildingTag(), item.getName());
        intent.putExtra("mapName", mapName);
        fragment.startMapActivity(intent);
    }
}
