package eu.warble.pjapp.ui.map;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.indoorway.android.fragments.sdk.map.IndoorwayMapFragment;
import com.indoorway.android.fragments.sdk.map.MapFragment;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;

public class MapActivity extends BaseActivity<MapPresenter> implements IndoorwayMapFragment.OnMapFragmentReadyListener {

    private ProgressBar progressBar;
    private View map;
    MapFragment mapFragment;

    @Override
    protected MapPresenter createPresenter() {
        MapPresenter presenter = new MapPresenter(this);
        presenter.setMapParameters(getIntent().getStringExtra("buildingUUID"),
                getIntent().getStringExtra("mapUUID"));
        return presenter;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_map);
        progressBar = findViewById(R.id.progressBar);
        map = findViewById(R.id.mapFragment);
        initActionBar();
    }

    @Override
    public void onMapFragmentReady(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        //presenter.loadMap(mapFragment);
    }

    void setLoadingState(boolean state){
        map.setVisibility(state ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void initActionBar(){
        ActionBar bar = getSupportActionBar();
        if (bar != null){
            bar.setTitle(getIntent().getStringExtra("mapName"));
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
