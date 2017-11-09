package eu.warble.pjapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class MainPresenter extends BaseActivityPresenter<MainActivity> {

    public MainPresenter(MainActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        checkNoInternetMode();
    }

    private void checkNoInternetMode() {
        //if noInternetMode enabled
        if (!activity.getIntent().getBooleanExtra("internet", true)){
            activity.showToast("No internet mode enabled");
        }
    }

    void logOut() {
        StudentDataRepository repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(CredentialsManager.getInstance().getCredentials(activity.getApplicationContext()), new AppExecutors()),
                StudentLocalDataSource.getInstance(new AppExecutors())
        );
        repository.deleteAllLocalStudentData();
        StudentDataRepository.destroyInstance();
        CredentialsManager.getInstance().deleteCredentials(activity.getApplicationContext());
        activity.startLoginActivity();
    }
}
