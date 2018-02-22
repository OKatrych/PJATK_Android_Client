package eu.warble.pjappkotlin.utils

import android.content.Context
import eu.warble.pjappkotlin.data.ScheduleDataRepository
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.remote.PjatkAPI


object Injection {

    fun provideStudentDataRepository(appContext: Context): StudentDataRepository? {
        CredentialsManager.getCredentials(appContext)?.let {
            return StudentDataRepository.getInstance(PjatkAPI.getInstance(it))
        }
        return null
    }

    fun provideScheduleDataRepository(appContext: Context): ScheduleDataRepository? {
        CredentialsManager.getCredentials(appContext)?.let {
            return ScheduleDataRepository.getInstance(PjatkAPI.getInstance(it))
        }
        return null
    }
}