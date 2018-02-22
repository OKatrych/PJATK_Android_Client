package eu.warble.pjappkotlin.mvp.schedule

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.fragment_schedule.date_picker
import kotlinx.android.synthetic.main.fragment_schedule.loading_screen
import kotlinx.android.synthetic.main.fragment_schedule.schedule_list
import kotlinx.android.synthetic.main.fragment_schedule.view.schedule_list
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
        initScheduleAdapter(view)
        initPresenter()
        return view
    }

    private fun initScheduleAdapter(view: View) {
        view.schedule_list.layoutManager = LinearLayoutManager(mContext)
        view.schedule_list.adapter = ScheduleListAdapter(mContext, emptyList())
        view.schedule_list.invalidate()
    }

    override fun showCalendarView(show: Boolean) {
        //TODO
    }

    override fun updateList(newItems: List<ZajeciaItem>) {
        (schedule_list.adapter as ScheduleListAdapter).updateList(newItems)
    }

    override fun showLoadingScreen(show: Boolean) {
        loading_screen.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initPresenter() {
        presenter = SchedulePresenter(
                Injection.provideScheduleDataRepository(mContext as Context),
                this,
                mContext as Context
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    companion object {

        fun newInstance() = ScheduleFragment()

    }
}