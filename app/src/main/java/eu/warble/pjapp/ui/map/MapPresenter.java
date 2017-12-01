package eu.warble.pjapp.ui.map;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.indoorway.android.common.sdk.IndoorwaySdk;
import com.indoorway.android.common.sdk.listeners.position.OnPositionChangedListener;
import com.indoorway.android.common.sdk.model.IndoorwayPosition;
import com.indoorway.android.common.sdk.model.Sex;
import com.indoorway.android.common.sdk.model.Visitor;
import com.indoorway.android.fragments.sdk.map.MapFragment;
import eu.warble.pjapp.R;
import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class MapPresenter extends BaseActivityPresenter<MapActivity>{

    private String buildingUUID;
    private String mapUUID;
    private MapFragment mapFragment;
    //private Action1<IndoorwayLocationSdkState> locationListener;

    MapPresenter(MapActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            buildingUUID = savedInstanceState.getString("buildingUUID");
            mapUUID = savedInstanceState.getString("mapUUID");
        }
        loadMap(activity.mapFragment);
        registerVisitor();
        //initLocationListener();
    }

    private void loadMap(MapFragment mapFragment) {
        activity.setLoadingState(true);
        this.mapFragment = mapFragment;
        mapFragment.getMapView().setOnMapLoadFailedListener(this::onMapLoadFailed);
        mapFragment.getMapView().setOnMapLoadCompletedListener(indoorwayMap -> {
            activity.setLoadingState(false);
            mapFragment.startPositioningService();
        });
        mapFragment.getMapView().loadMap(buildingUUID, mapUUID);
        /*mapFragment.getPositioningServiceConnection().setOnPositionChangedListener(new OnPositionChangedListener() {
            @Override
            public void onPositionChanged(IndoorwayPosition indoorwayPosition) {

            }
        });*/
    }

    void setMapParameters(String buildingUUID, String mapUUID){
        this.mapUUID = mapUUID;
        this.buildingUUID = buildingUUID;
    }

    /*private void initLocationListener(){
        locationListener = indoorwayLocationSdkState -> {
            Log.i("locationListener", indoorwayLocationSdkState.toString());
        };
    }*/

    /*private void registerListener(){
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .register(locationListener);
    }

    private void unRegisterListener(){
        IndoorwayLocationSdk.instance()
                .state()
                .onChange()
                .unregister(locationListener);
    }*/

    void onSaveInstanceState(Bundle outState){
        outState.putString("buildingUUID", buildingUUID);
        outState.putString("mapUUID", mapUUID);
    }

    private void onMapLoadFailed() {
        activity.setLoadingState(false);
        activity.showError(activity.getString(R.string.map_loading_error), true);
    }

    void onResume(){
        //registerListener();
    }

    @Override
    protected void onDestroyActivity() {
        //unRegisterListener();
        mapFragment.getMapView().setOnMapLoadFailedListener(null);
        mapFragment.getMapView().setOnMapLoadCompletedListener(null);
        super.onDestroyActivity();
    }

    private void registerVisitor(){
        AppExecutors executors = new AppExecutors();
        StudentDataRepository.getInstance(
                PjatkAPI.getInstance(CredentialsManager.getInstance().getCredentials(activity), executors),
                StudentLocalDataSource.getInstance(executors))
                .getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                Visitor visitor = new Visitor();
                visitor.setGroupUuid("pj_android_app_users");
                visitor.setName(String.format("%s %s", studentData.getImie(), studentData.getNazwisko()));
                visitor.setUuid(studentData.getLogin());
                visitor.setSex(Sex.UNKNOWN);
                IndoorwaySdk.getInstance().setupVisitor(visitor);
            }

            @Override
            public void onDataNotAvailable(String error) {
                activity.showError(error, false);
            }
        });
    }
}
