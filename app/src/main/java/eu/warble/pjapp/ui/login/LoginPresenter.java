package eu.warble.pjapp.ui.login;


import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.PreferencesManager;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.CredentialsManager.Credentials;

public class LoginPresenter extends BaseActivityPresenter<LoginActivity> {

    public LoginPresenter(LoginActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {}

    public void login(String login, String password){
        AppExecutors executors = new AppExecutors();
        StudentDataRepository repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(new Credentials(login, password), executors),
                StudentLocalDataSource.getInstance(executors)
        );
        repository.getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                //login successful
                CredentialsManager.getInstance().saveCredentials(login, password, activity.getApplicationContext());
                activity.startMainActivity();
            }

            @Override
            public void onDataNotAvailable(String error) {
                switch (error){
                    case Constants.CREDENTIALS_ERROR:
                        PjatkAPI.destroyInstance();
                        StudentDataRepository.destroyInstance();
                        activity.showError(R.string.login_error);
                        break;
                    case Constants.CONNECTION_ERROR:
                        activity.showError(R.string.connect_error);
                        break;
                    default:
                        activity.showError(error);
                }
            }
        });
    }
}
