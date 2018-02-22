package eu.warble.pjappkotlin.mvp.schedule

import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView
import org.threeten.bp.LocalDate
import solar.blaz.date.week.WeekDatePicker


interface ScheduleContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        val datePicker: WeekDatePicker
        fun showCalendarView(show: Boolean)
        fun updateList(newItems: List<ZajeciaItem>)
        fun showLoadingScreen(show: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadWeekScheduleForSelectedDay(day: LocalDate)
        fun loadCurrentWeekSchedule()
    }

}