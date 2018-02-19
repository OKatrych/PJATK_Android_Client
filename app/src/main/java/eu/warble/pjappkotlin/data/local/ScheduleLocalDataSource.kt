package eu.warble.pjappkotlin.data.local

import android.content.Context
import eu.warble.pjappkotlin.data.ScheduleDataSource
import org.threeten.bp.LocalDate


object ScheduleLocalDataSource : ScheduleDataSource {
    override fun getScheduleData(
            appContext: Context,
            from: LocalDate,
            to: LocalDate,
            callback: ScheduleDataSource.LoadScheduleDataCallback
    ) {
        //TODO error when not found

    }
}