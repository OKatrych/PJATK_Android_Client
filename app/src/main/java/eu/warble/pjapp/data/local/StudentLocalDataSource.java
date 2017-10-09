package eu.warble.pjapp.data.local;


import android.content.Context;
import android.support.annotation.NonNull;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.Converter;

public class StudentLocalDataSource implements StudentDataSource{

    private static volatile StudentLocalDataSource INSTANCE;

    private AppExecutors appExecutors;

    // Prevent direct instantiation.
    private StudentLocalDataSource(@NonNull AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public static StudentLocalDataSource getInstance(@NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (StudentLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StudentLocalDataSource(appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getStudentData(@NonNull LoadStudentDataCallback callback) {
        appExecutors.diskIO().execute(() -> {
            Context context = Application.getAppContext();
            if (context == null)
                appExecutors.mainThread().execute(() -> callback.onDataNotAvailable(Constants.CONTEXT_ERROR));
            else {
                String json = PreferencesManager.getStudentData(context);
                Student studentData = Converter.jsonStringToStudentData(json);
                appExecutors.mainThread().execute(() ->{
                    if (studentData != null)
                        callback.onDataLoaded(studentData);
                    else
                        callback.onDataNotAvailable(Constants.EMPTY_ERROR);
                });
            }
        });
    }

    public void saveStudentData(@NonNull Student studentData) {
        appExecutors.diskIO().execute(() -> {
            Context context = Application.getAppContext();
            if (context != null)
                PreferencesManager.saveStudentData(context, Converter.studentDataToJsonString(studentData));
        });
    }

    public void deleteAllStudentData() {
        appExecutors.diskIO().execute(() -> {
            Context context = Application.getAppContext();
            if (context != null)
                PreferencesManager.saveStudentData(context, null);
        });
    }
}
