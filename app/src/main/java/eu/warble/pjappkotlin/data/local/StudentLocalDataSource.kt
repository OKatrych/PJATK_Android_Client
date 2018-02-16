package eu.warble.pjappkotlin.data.local

import android.app.Activity
import android.content.Context
import eu.warble.pjappkotlin.utils.Constants.EMPTY_ERROR
import eu.warble.pjappkotlin.utils.Constants.PJAPP_PREFS
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.AppExecutors
import eu.warble.pjappkotlin.utils.Converter

object StudentLocalDataSource : StudentDataSource {

    override fun getStudentData(appContext: Context, callback: StudentDataSource.LoadStudentDataCallback) {
        AppExecutors.DISK().execute {
            val preferences = appContext.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE)
            val jsonData = preferences.getString("student_data", null)
            val studentData = Converter.jsonStringToStudentData(jsonData)
            AppExecutors.MAIN().execute {
                if (studentData != null)
                    callback.onDataLoaded(studentData)
                else
                    callback.onDataNotAvailable(EMPTY_ERROR)
            }
        }
    }

    fun saveStudentData(appContext: Context, studentData: Student) {
        AppExecutors.DISK().execute {
            val preferences = appContext.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE)
            preferences.edit()
                    .putString("student_data", Converter.studentDataToJsonString(studentData))
                    .apply()
        }
    }

    fun deleteAllStudentData(appContext: Context) {
        AppExecutors.DISK().execute {
            val preferences = appContext.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE)
            preferences.edit()
                    .putString("student_data", null)
                    .apply()
        }
    }

}