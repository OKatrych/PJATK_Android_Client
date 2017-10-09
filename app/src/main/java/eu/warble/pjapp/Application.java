package eu.warble.pjapp;

import android.annotation.SuppressLint;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

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