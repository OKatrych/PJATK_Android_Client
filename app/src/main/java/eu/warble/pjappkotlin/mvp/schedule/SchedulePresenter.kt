package eu.warble.pjappkotlin.mvp.schedule

import android.content.Context
import eu.warble.pjappkotlin.data.ScheduleDataRepository
import org.threeten.bp.LocalDate

class SchedulePresenter(
        private val scheduleDataRepository: ScheduleDataRepository,
        val view: ScheduleContract.View,
        private val appContext: Context
) : ScheduleContract.Presenter {

    override fun start() {
        loadCurrentWeekSchedule()
    }

    override fun loadCurrentWeekSchedule() {
        val currentDay = LocalDate.now()
        loadWeekScheduleForSelectedDay(currentDay)
    }

    override fun loadWeekScheduleForSelectedDay(day: LocalDate) {
        view.showLoadingScreen(true)

    }

    override fun refresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}