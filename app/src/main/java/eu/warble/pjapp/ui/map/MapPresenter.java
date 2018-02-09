package eu.warble.pjapp.ui.map;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.indoorway.android.common.sdk.IndoorwaySdk;
import com.indoorway.android.common.sdk.model.IndoorwayObjectParameters;
import com.indoorway.android.common.sdk.model.Sex;
import com.indoorway.android.common.sdk.model.Visitor;
import com.indoorway.android.fragments.sdk.map.MapFragment;
import com.indoorway.android.map.sdk.listeners.OnObjectSelectedListener;

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
    private OnObjectSelectedListener objectSelectedListener;

    MapPresenter(MapActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            buildingUUID = savedInstanceState.getString("buildingUUID");
            mapUUID = savedInstanceState.getString("mapUUID");
        }
        objectSelectedListener = initObjSelectedListener();
        loadMap(activity.mapFragment);
        registerVisitor();
    }

    private void loadMap(MapFragment mapFragment) {
        activity.setLoadingState(true);
        this.mapFragment = mapFragment;
        mapFragment.getMapView().setOnMapLoadFailedListener(this::onMapLoadFailed);
        mapFragment.getMapView().setOnMapLoadCompletedListener(indoorwayMap -> {
            activity.setLoadingState(false);
            mapFragment.startPositioningService();
        });
        mapFragment.getMapView().getSelection().setOnObjectSelectedListener(objectSelectedListener);
        mapFragment.getMapView().load(buildingUUID, mapUUID);
    }

    void setMapParameters(String buildingUUID, String mapUUID){
        this.mapUUID = mapUUID;
        this.buildingUUID = buildingUUID;
    }

    void onSaveInstanceState(Bundle outState){
        outState.putString("buildingUUID", buildingUUID);
        outState.putString("mapUUID", mapUUID);
    }

    private void onMapLoadFailed() {
        activity.setLoadingState(false);
        activity.showError(activity.getString(R.string.map_loading_error), true);
    }

    @Override
    protected void onDestroyActivity() {
        mapFragment.getMapView().setOnMapLoadFailedListener(null);
        mapFragment.getMapView().setOnMapLoadCompletedListener(null);
        mapFragment.getMapView().getSelection().setOnObjectSelectedListener(null);
        super.onDestroyActivity();
    }

    private void registerVisitor(){
        AppExecutors executors = new AppExecutors();
        CredentialsManager.Credentials credentials = CredentialsManager.getInstance().getCredentials(activity);
        if (credentials == null){
            Visitor visitor = new Visitor();
            visitor.setGroupUuid("pj_android_app_users");
            visitor.setName("Guest mode");
            visitor.setSex(Sex.UNKNOWN);
            IndoorwaySdk.instance().visitor().setup(visitor);
            return;
        }
        StudentDataRepository.getInstance(
                PjatkAPI.getInstance(credentials, executors),
                StudentLocalDataSource.getInstance(executors))
                .getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                Visitor visitor = new Visitor();
                visitor.setGroupUuid("pj_android_app_users");
                visitor.setName(String.format("%s %s %s", studentData.getImie(), studentData.getNazwisko()
                        , studentData.getLogin()));
                visitor.setSex(Sex.UNKNOWN);
                IndoorwaySdk.instance().visitor().setup(visitor);
            }

            @Override
            public void onDataNotAvailable(String error) {
                activity.showError(error, false);
            }
        });
    }

    private OnObjectSelectedListener initObjSelectedListener(){
        return new OnObjectSelectedListener() {
            @Override
            public boolean canObjectBeSelected(IndoorwayObjectParameters parameters) {
                return !"inaccessible".equals(parameters.getType());
            }

            @Override
            public void onObjectSelected(IndoorwayObjectParameters parameters) {
                mapFragment.getMapView().getNavigation().start(parameters.getId());
            }

            @Override
            public void onSelectionCleared() {
                mapFragment.getMapView().getNavigation().stop();
            }
        };
    }
}
