package eu.warble.pjapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.Tools;

public class MainPresenter extends BaseActivityPresenter<MainActivity> {

    public MainPresenter(MainActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        checkNoInternetMode();
        checkApiResponseForErrors();
    }

    void checkApiResponseForErrors() {
        AppExecutors executors = new AppExecutors();
        StudentDataRepository.getInstance(
                PjatkAPI.getInstance(CredentialsManager.getInstance().getCredentials(activity), executors),
                StudentLocalDataSource.getInstance(executors)).getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                if (!Tools.checkApiResponseForErrors(studentData))
                    activity.showApiError(null);
            }

            @Override
            public void onDataNotAvailable(String error) {
                activity.showApiError(error);
            }
        });
    }

    private void checkNoInternetMode() {
        //if noInternetMode enabled
        if (!activity.getIntent().getBooleanExtra("internet", true)){
            activity.showToast(activity.getString(R.string.no_internet_mode_enabled));
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
