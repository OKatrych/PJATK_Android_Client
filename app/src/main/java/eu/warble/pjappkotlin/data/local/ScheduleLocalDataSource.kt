package eu.warble.pjappkotlin.data.local

import android.content.Context
import eu.warble.pjappkotlin.data.ScheduleDataSource
import eu.warble.pjappkotlin.utils.Constants
import org.threeten.bp.LocalDate


object ScheduleLocalDataSource : ScheduleDataSource {
    override fun getScheduleData(
            appContext: Context,
            from: LocalDate,
            to: LocalDate,
            callback: ScheduleDataSource.LoadScheduleDataCallback
    ) {
        //TODO error when not found
        callback.onDataNotAvailable(Constants.EMPTY_ERROR)

    }
}