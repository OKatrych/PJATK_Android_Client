package eu.warble.pjappkotlin.mvp.schedule

import android.content.Context
import eu.warble.pjappkotlin.data.ScheduleDataRepository
import eu.warble.pjappkotlin.data.ScheduleDataSource
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.utils.ScheduleManager
import eu.warble.pjappkotlin.view.WeekDatePicker
import org.threeten.bp.LocalDate

class SchedulePresenter(
        private val scheduleDataRepository: ScheduleDataRepository?,
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
        val from = day.minusDays(day.dayOfWeek.value - 1L)
        val to = day.plusDays(7L - day.dayOfWeek.value)
        scheduleDataRepository?.getScheduleData(appContext, from, to, object : ScheduleDataSource.LoadScheduleDataCallback {
            override fun onDataLoaded(scheduleData: List<ZajeciaItem>) {
                view.showLoadingScreen(false)
                showSchedule(day, from, to, scheduleData)
            }

            override fun onDataNotAvailable(error: String) {
                view.showLoadingScreen(false)
                view.showError(error)
            }
        })
    }

    private fun showSchedule(selectedDay: LocalDate, from: LocalDate, to: LocalDate, scheduleData: List<ZajeciaItem>) {
        val datePicker = view.datePicker
        val scheduleManager = ScheduleManager(scheduleData)
        datePicker.setOnDateSelectedListener(object : WeekDatePicker.OnDaySelectedListener {
            override fun onDaySelected(selectedDay: LocalDate) {
                view.updateList(scheduleManager.getLessonsForDate(selectedDay))
            }
        })
        datePicker.updateDates(selectedDay)
    }
}