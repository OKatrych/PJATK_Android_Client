package eu.warble.pjappkotlin.mvp.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_schedule.date_picker
import kotlinx.android.synthetic.main.fragment_schedule.loading_screen
import solar.blaz.date.week.WeekDatePicker


class ScheduleFragment : BaseFragment(), ScheduleContract.View {

    override val TAG: String = "fragment_schedule"

    override lateinit var presenter: ScheduleContract.Presenter
    override val datePicker: WeekDatePicker by lazy { date_picker }
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        initPresenter()
        return view
    }

    override fun showCalendarView(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateList(newItems: List<ZajeciaItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingScreen(show: Boolean) {
        loading_screen.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}