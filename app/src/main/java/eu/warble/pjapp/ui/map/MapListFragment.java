package eu.warble.pjapp.ui.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.indoorway.android.common.sdk.model.IndoorwayBuilding;

import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.MapListItem;
import eu.warble.pjapp.ui.base.BaseFragment;

public class MapListFragment extends BaseFragment<MapListPresenter> {

    private ListView mapList;
    private ProgressBar progressBar;

    @Override
    protected MapListPresenter createPresenter() {
        return new MapListPresenter(this);
    }

    public static MapListFragment newInstance() {
        Bundle args = new Bundle();
        MapListFragment fragment = new MapListFragment();
        fragment.setArguments(args);
        fragment.tag = "MapListFragment";
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);
        setHasOptionsMenu(true);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        MapListAdapter adapter = new MapListAdapter(context.getApplicationContext());
        progressBar = view.findViewById(R.id.progressBar);
        mapList = view.findViewById(R.id.list_maps);
        mapList.setAdapter(adapter);
        mapList.setOnItemClickListener((parent, view1, position, id) -> {
            MapListItem item = adapter.getItem(position);
            presenter.onMapItemClicked(item);
        });
    }

    public void updateList(List<IndoorwayBuilding> newData) {
        MapListAdapter adapter = (MapListAdapter) mapList.getAdapter();
        adapter.updateList(newData);
    }

    void setLoadingState(boolean state){
        mapList.setVisibility(state ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    void startMapActivity(Intent intent){
        startActivity(intent);
    }
}
