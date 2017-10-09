package eu.warble.pjapp.data;


import android.support.annotation.NonNull;

import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;

public class StudentDataRepository implements StudentDataSource{

    private static StudentDataRepository INSTANCE = null;

    private final PjatkAPI studentRemoteDataSource;

    private final StudentLocalDataSource studentLocalDataSource;

    private Student cachedData;

    private boolean cacheIsDirty = false;

    // Prevent direct instantiation.
    private StudentDataRepository(@NonNull PjatkAPI studentRemoteDataSource,
                                  @NonNull StudentLocalDataSource studentLocalDataSource) {
        this.studentRemoteDataSource = studentRemoteDataSource;
        this.studentLocalDataSource = studentLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param studentRemoteDataSource the backend data source
     * @param studentLocalDataSource  the device storage data source
     * @return the {@link StudentDataRepository} instance
     */
    public static StudentDataRepository getInstance(@NonNull PjatkAPI studentRemoteDataSource,
                                                    @NonNull StudentLocalDataSource studentLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new StudentDataRepository(studentRemoteDataSource, studentLocalDataSource);
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(PjatkAPI, StudentLocalDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        PjatkAPI.destroyInstance();
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadStudentDataCallback#onDataNotAvailable(String s)} is fired if all data sources fail to
     * get the data.
     */
    public void getStudentData(@NonNull final LoadStudentDataCallback callback) {

        // Respond immediately with cache if available and not dirty
        if (cachedData != null && !cacheIsDirty) {
            callback.onDataLoaded(cachedData);
            return;
        }

        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getStudentDataFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            studentLocalDataSource.getStudentData(new LoadStudentDataCallback() {
                @Override
                public void onDataLoaded(Student studentData) {
                    refreshCache(studentData);
                    callback.onDataLoaded(studentData);
                }

                @Override
                public void onDataNotAvailable(String error) {
                    getStudentDataFromRemoteDataSource(callback);
                }
            });
        }
    }

    public void saveStudentData(@NonNull Student studentData) {
        studentLocalDataSource.saveStudentData(studentData);

        // Do in memory cache update to keep the app UI up to date
        if (cachedData == null) {
            cachedData = studentData;
        }
    }

    public void refreshStudentData() {
        cacheIsDirty = true;
    }

    public void deleteAllLocalStudentData() {
        studentLocalDataSource.deleteAllStudentData();
        cachedData = null;
    }

    private void getStudentDataFromRemoteDataSource(@NonNull final LoadStudentDataCallback callback) {
        studentRemoteDataSource.getStudentData(new LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                refreshCache(studentData);
                refreshLocalDataSource(studentData);
                callback.onDataLoaded(studentData);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    private void refreshCache(Student studentData) {
        cachedData = null;
        cachedData = studentData;
        cacheIsDirty = false;
    }

    private void refreshLocalDataSource(Student studentData) {
        studentLocalDataSource.deleteAllStudentData();
        studentLocalDataSource.saveStudentData(studentData);
    }
}
