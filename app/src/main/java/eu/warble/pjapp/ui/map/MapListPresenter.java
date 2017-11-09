package eu.warble.pjapp.ui.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.MapListItem;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;

public class MapListPresenter extends BaseFragmentPresenter<MapListFragment> {


    public MapListPresenter(MapListFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {}


    public void onItemClicked(MapListItem item) {
        Intent intent = new Intent(fragment.getActivity(), MapActivity.class);
        intent.putExtra("mapUUID", item.getMapUUID());
        intent.putExtra("buildingUUID", item.getBuildingUUID());
        String mapName = String.format("%s %s: %s", fragment.getString(R.string.building), item.getBuildingTag(), item.getName());
        intent.putExtra("mapName", mapName);
        fragment.startMapActivity(intent);
    }
}
