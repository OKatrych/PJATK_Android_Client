package eu.warble.pjapp.data;


import eu.warble.pjapp.data.model.Student;

public interface StudentDataSource {

    interface LoadStudentDataCallback {
        void onDataLoaded(Student studentData);
        void onDataNotAvailable(String error);
    }

    void getStudentData(LoadStudentDataCallback callback);
}
