package eu.warble.pjapp.ui.main.map;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indoorway.android.map.sdk.view.MapView;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;

public class MapFragment extends BaseFragment<MapPresenter> {

    MapView mapView;

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter(this);
    }

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        fragment.tag = "MapFragment";
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initViews(view);
        loadMap();
        return view;
    }

    private void initViews(View view) {
        mapView = view.findViewById(R.id.mapView);
    }

    private void loadMap() {
        mapView.setOnMapLoadCompletedListener(indoorwayMap -> {
            Log.i("Map", "OnMapLoadCompleted");
        });

        mapView.setOnMapLoadFailedListener(() -> {
            Log.e("Map", "OnMapLoadFailed");
        });

        mapView.loadMap("_hfEEWbSOT0", "eFKBL1M2G_g");
    }
}
