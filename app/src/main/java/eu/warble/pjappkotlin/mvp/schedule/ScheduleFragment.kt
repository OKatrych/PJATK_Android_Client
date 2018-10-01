package eu.warble.pjappkotlin.mvp.schedule

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Injection
import eu.warble.pjappkotlin.view.WeekDatePicker
import kotlinx.android.synthetic.main.fragment_schedule.view.calendar_fab
import kotlinx.android.synthetic.main.fragment_schedule.view.empty_lessons_text
import kotlinx.android.synthetic.main.fragment_schedule.view.loading_screen
import kotlinx.android.synthetic.main.fragment_schedule.view.schedule_list
import kotlinx.android.synthetic.main.fragment_schedule.view.week_date_picker
import org.threeten.bp.LocalDate
import java.util.Calendar

class ScheduleFragment : BaseFragment(), ScheduleContract.View {

    override val TAG: String = "fragment_schedule"

    override lateinit var presenter: ScheduleContract.Presenter
    override lateinit var datePicker: WeekDatePicker
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }
    private lateinit var scheduleList: RecyclerView
    private lateinit var calendarFAB: FloatingActionButton
    private lateinit var emptyText: TextView
    private lateinit var loadingScreen: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        initViews(view)
        initScheduleList(view)
        initPresenter()
        return view
    }

    private fun initViews(view: View) {
        initFAB(view)
        emptyText = view.empty_lessons_text
        loadingScreen = view.loading_screen
        datePicker = view.week_date_picker
    }

    private fun initScheduleList(view: View) {
        scheduleList = view.schedule_list
        scheduleList.layoutManager = LinearLayoutManager(mContext)
        scheduleList.adapter = ScheduleListAdapter(mContext, emptyList())
        scheduleList.invalidate()
        scheduleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                when {
                    dy > 0 -> calendarFAB.hide()
                    dy < 0 -> calendarFAB.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initFAB(view: View) {
        calendarFAB = view.calendar_fab
        mContext?.let {
            val color = ContextCompat.getColor(it, R.color.white)
            val icon = ContextCompat.getDrawable(it, R.drawable.calendar)?.apply {
                setTint(color)
            }
            calendarFAB.setImageDrawable(icon)
        }
        calendarFAB.setOnClickListener {
            showCalendarView()
        }
    }

    override fun showCalendarView() {
        val now = LocalDate.now()
        DatePickerDialog(
                mContext,
                R.style.DatePickerStyle,
                onCalendarDateSelected(),
                now.year,
                now.monthValue - 1,
                now.dayOfMonth
        ).apply {
            datePicker.firstDayOfWeek = Calendar.MONDAY
        }.show()
    }

    override fun updateList(newItems: List<ZajeciaItem>) {
        (scheduleList.adapter as ScheduleListAdapter).updateList(newItems)
        if (newItems.isEmpty()) {
            emptyText.visibility = View.VISIBLE
        } else {
            emptyText.visibility = View.GONE
        }
    }

    override fun showLoadingScreen(show: Boolean) {
        if (show) calendarFAB.hide() else calendarFAB.show()
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initPresenter() {
        presenter = SchedulePresenter(
                Injection.provideScheduleDataRepository(mContext as Context),
                this,
                mContext as Context
        )
        presenter.start()
    }

    private fun onCalendarDateSelected() = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        presenter.loadWeekScheduleForSelectedDay(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
    }

    companion object {

        fun newInstance() = ScheduleFragment()

    }
}