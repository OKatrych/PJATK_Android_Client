package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.StudentDataSource.LoadStudentDataCallback
import eu.warble.pjappkotlin.data.local.StudentLocalDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.data.remote.PjatkAPI


class StudentDataRepository private constructor(
        private val studentRemoteDataSource: StudentDataSource
) : StudentDataSource {

    private val studentLocalDataSource = StudentLocalDataSource

    private var cachedData: Student? = null

    private var cacheIsDirty = false

    override fun getStudentData(appContext: Context, callback: LoadStudentDataCallback) {
        // Respond immediately with cache if available and not dirty
        val mCachedData = cachedData
        if (mCachedData != null && !cacheIsDirty) {
            callback.onDataLoaded(mCachedData)
            return
        }
        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getStudentDataFromRemoteDataSource(appContext, callback)
        } else {
            // Query the local storage if available. If not, query the network.
            studentLocalDataSource.getStudentData(appContext, object : LoadStudentDataCallback {
                override fun onDataLoaded(studentData: Student) {
                    refreshCache(studentData)
                    callback.onDataLoaded(studentData)
                }

                override fun onDataNotAvailable(error: String) {
                    getStudentDataFromRemoteDataSource(appContext, callback)
                }
            })
        }
    }

    fun saveStudentData(appContext: Context, studentData: Student) {
        studentLocalDataSource.saveStudentData(appContext, studentData)
        // Do in memory cache update to keep the app UI up to date
        if (cachedData == null) {
            cachedData = studentData
        }
    }

    fun refreshStudentData() {
        cacheIsDirty = true
    }

    fun deleteAllLocalStudentData(appContext: Context) {
        studentLocalDataSource.deleteAllStudentData(appContext)
        cachedData = null
    }

    private fun getStudentDataFromRemoteDataSource(appContext: Context, callback: LoadStudentDataCallback) {
        studentRemoteDataSource.getStudentData(appContext, object : LoadStudentDataCallback {
            override fun onDataLoaded(studentData: Student) {
                refreshCache(studentData)
                refreshLocalDataSource(appContext, studentData)
                callback.onDataLoaded(studentData)
            }

            override fun onDataNotAvailable(error: String) {
                callback.onDataNotAvailable(error)
            }
        })
    }

    private fun refreshCache(studentData: Student) {
        cachedData = null
        cachedData = studentData
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(appContext: Context, studentData: Student) {
        studentLocalDataSource.deleteAllStudentData(appContext)
        studentLocalDataSource.saveStudentData(appContext, studentData)
    }

    companion object {
        private var INSTANCE: StudentDataRepository? = null

        @JvmStatic
        fun getInstance(studentRemoteDataSource: StudentDataSource): StudentDataRepository {
            return INSTANCE ?: StudentDataRepository(studentRemoteDataSource).apply {
                INSTANCE = this
            }
        }

        @JvmStatic
        fun destroyInstance() {
            PjatkAPI.destroyInstance()
            INSTANCE = null
        }
    }
}