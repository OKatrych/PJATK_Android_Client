package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.model.Student

interface StudentDataSource {

    interface LoadStudentDataCallback {
        fun onDataLoaded(studentData: Student)
        fun onDataNotAvailable(error: String)
    }

    fun getStudentData(appContext: Context, callback: LoadStudentDataCallback)
}