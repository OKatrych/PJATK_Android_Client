package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import org.threeten.bp.LocalDate

interface ScheduleDataSource {

    interface LoadScheduleDataCallback {
        fun onDataLoaded(scheduleData: List<ZajeciaItem>)
        fun onDataNotAvailable(error: String)
    }

    fun getScheduleData(
            appContext: Context,
            from: LocalDate,
            to: LocalDate,
            callback: LoadScheduleDataCallback
    )
}