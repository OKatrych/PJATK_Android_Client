package eu.warble.pjappkotlin.mvp.schedule

import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView
import eu.warble.pjappkotlin.view.WeekDatePicker
import org.threeten.bp.LocalDate


interface ScheduleContract {

    interface View : BaseView<Presenter> {
        val applicationNavigator: ApplicationNavigator
        var datePicker: WeekDatePicker
        fun showCalendarView()
        fun updateList(newItems: List<ZajeciaItem>)
        fun showLoadingScreen(show: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadWeekScheduleForSelectedDay(day: LocalDate)
        fun loadCurrentWeekSchedule()
    }

}