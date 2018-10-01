package eu.warble.pjappkotlin.view

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import eu.warble.pjappkotlin.R
import org.threeten.bp.LocalDate


class WeekDatePicker(
        context: Context,
        @Nullable attr: AttributeSet
) : LinearLayout(context, attr), View.OnClickListener {

    private var onDaySelectedListener: OnDaySelectedListener? = null

    private val daysViews: Array<View> by lazy {
        arrayOf(
                findViewById<View>(R.id.day1),
                findViewById<View>(R.id.day2),
                findViewById<View>(R.id.day3),
                findViewById<View>(R.id.day4),
                findViewById<View>(R.id.day5),
                findViewById<View>(R.id.day6),
                findViewById<View>(R.id.day7)
        )
    }
    private val days: ArrayList<LocalDate> = ArrayList(7)
    private val daysDates: ArrayList<TextView> = ArrayList(7)
    private var selectedDay = LocalDate.now()

    init {
        orientation = LinearLayout.HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.week_date_picker, this, true)
        gravity = Gravity.CENTER
        setPadding(16, 0, 16, 8)
        initDays()
    }

    /**
     * @param day - any day (week will be calculated on the bases of this day)
     */
    fun updateDates(day: LocalDate) {
        val from = day.minusDays(day.dayOfWeek.value - 1L)
        for (i in 0..6) {
            days[i] = from.plusDays(i.toLong())
            daysDates[i].text = days[i].dayOfMonth.toString()
        }
        selectDay(day)
    }

    fun selectDay(day: LocalDate) {
        var contains = false
        for ((index, date) in days.withIndex()) {
            if (date.isEqual(day)) {
                contains = true
                onDaySelectedListener?.onDaySelected(day)
                setContainerIndicator(index)
                selectedDay = day
            }
        }
        if (!contains)
            Log.e("WeekDatePicker", "Given date not exists in array")
    }

    fun setOnDateSelectedListener(onDaySelectedListener: OnDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener
    }

    override fun onClick(view: View) {
        for ((index, container) in daysViews.withIndex()) {
            if (container.id == view.id) {
                setContainerIndicator(index)
                onDaySelectedListener?.onDaySelected(days[index])
            }
        }
    }

    private fun setContainerIndicator(index: Int) {
        daysViews.forEach {
            it.findViewById<FrameLayout>(R.id.day_of_month_container)
                    .setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
        daysViews[index].findViewById<FrameLayout>(R.id.day_of_month_container)
                .background = ContextCompat.getDrawable(context, R.drawable.date_picker_indicator)
    }

    private fun initDays() {
        val dayNames = arrayOf(R.string.monday, R.string.tuesday, R.string.wednesday, R.string.thursday,
                R.string.friday, R.string.saturday, R.string.sunday)
        val beginOfWeek = LocalDate.now().minusDays(LocalDate.now().dayOfWeek.value - 1L)
        for ((i, day) in daysViews.withIndex()) {
            day.setOnClickListener(this)
            day.findViewById<TextView>(R.id.day_of_week_name).text = resources.getString(dayNames[i])
            daysDates.add(day.findViewById(R.id.day_of_month_value))
            days.add(i, beginOfWeek.plusDays(i.toLong()))
        }
        updateDates(selectedDay)
    }

    /*override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putLong("date", selectedDay.toEpochDay())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var mState = state
        if (mState is Bundle) {
            selectedDay = LocalDate.ofEpochDay(mState.getLong("date"))
            mState = mState.getParcelable("superState")
        }
        super.onRestoreInstanceState(mState)
    }*/

    interface OnDaySelectedListener {
        fun onDaySelected(selectedDay: LocalDate)
    }
}