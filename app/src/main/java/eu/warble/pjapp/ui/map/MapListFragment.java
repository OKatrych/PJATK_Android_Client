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

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.MapListItem;
import eu.warble.pjapp.ui.base.BaseFragment;

public class MapListFragment extends BaseFragment<MapListPresenter> {

    private ListView mapList;

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
        mapList = view.findViewById(R.id.list_maps);
        mapList.setAdapter(adapter);
        mapList.setOnItemClickListener((parent, view1, position, id) -> {
            MapListItem item = adapter.getItem(position);
            presenter.onItemClicked(item);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    void startMapActivity(Intent intent){
        startActivity(intent);
    }
}
