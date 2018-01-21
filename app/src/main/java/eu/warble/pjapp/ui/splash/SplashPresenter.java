package eu.warble.pjapp.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.CredentialsManager.Credentials;
import eu.warble.pjapp.util.NetworkHelper;

public class SplashPresenter extends BaseActivityPresenter<SplashActivity> {

    SplashPresenter(SplashActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        loadData();
    }

    void loadData(){
        Credentials credentials = CredentialsManager.getInstance()
                .getCredentials(activity.getApplicationContext());
        if (credentials == null) {
            activity.startLoginActivity();
            return;
        }
        AppExecutors executors = new AppExecutors();
        StudentDataRepository repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(credentials, executors),
                StudentLocalDataSource.getInstance(executors)
        );
        if (NetworkHelper.isNetworkAvailable(activity.getApplicationContext())){
            //fetch new data from the network
            repository.refreshStudentData();
            repository.getStudentData(new StudentDataSource.LoadStudentDataCallback() {
                @Override
                public void onDataLoaded(Student studentData) {
                    // sign in successful
                    activity.startMainActivity();
                }

                @Override
                public void onDataNotAvailable(String error) {
                    switch (error){
                        case Constants.CONNECTION_ERROR:
                            activity.showConnectionError();
                            break;
                        case Constants.CREDENTIALS_ERROR:
                            StudentDataRepository.destroyInstance();
                            activity.startLoginActivity();
                            break;
                        default:
                            activity.showMessage(error);
                    }
                }
            });
        }else {
            activity.startMainActivityWithNoInternetMode();
        }
    }
}
