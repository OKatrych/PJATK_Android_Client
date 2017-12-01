package eu.warble.pjapp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.indoorway.android.common.sdk.IndoorwaySdk;
import com.indoorway.android.location.sdk.IndoorwayLocationSdk;
import com.indoorway.android.map.sdk.IndoorwayMapSdk;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import eu.warble.pjapp.ui.map.PjMapViewConfig;

public class Application extends android.app.Application{

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static RefWatcher getRefWatcher(Context context){
        Application application = (Application) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
        //String trafficApiKey = "e0788b1a-6484-4229-b6cf-4e352bcdc630";
        String trafficApiKey = "acdd0179-3ea5-421b-8035-e96009d61a77";
        // sdk for map view and fetching map objects
        IndoorwayMapSdk.init(this, trafficApiKey);
        // sdk for indoor positioning
        IndoorwayLocationSdk.init(this, trafficApiKey);
        IndoorwayMapSdk.getInstance()
                .setMapViewConfig(new PjMapViewConfig(context));
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static Context getAppContext() {
        return Application.context;
    }

}