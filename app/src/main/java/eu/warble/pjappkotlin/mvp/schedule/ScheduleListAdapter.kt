package eu.warble.pjappkotlin.mvp.schedule

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_schedule.view.left_corner_color
import kotlinx.android.synthetic.main.list_item_schedule.view.schedule_lessonLocation
import kotlinx.android.synthetic.main.list_item_schedule.view.schedule_lessonName
import kotlinx.android.synthetic.main.list_item_schedule.view.schedule_lessonTime
import kotlinx.android.synthetic.main.list_item_schedule.view.schedule_lessonType
import kotlinx.android.synthetic.main.list_item_schedule.view.schedule_type_dot

class ScheduleListAdapter(private val context: Context?, scheduleData: List<ZajeciaItem>) : RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {

    var items = ArrayList<ZajeciaItem>(scheduleData)

    fun updateList(scheduleData: List<ZajeciaItem>) {
        items.clear()
        items.addAll(scheduleData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_schedule))
    }

    fun getTypeColor(lessonType: String?): Int {
        context?.let {
            return when (lessonType) {
                "Ćwiczenia" -> ContextCompat.getColor(context, R.color.lessonType1)
                "Wykład" -> ContextCompat.getColor(context, R.color.lessonType2)
                "Egzamin" -> ContextCompat.getColor(context, R.color.lessonType3)
                else -> ContextCompat.getColor(context, R.color.lessonType4)
            }
        }
        return -1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var leftCornerColor: View = itemView.left_corner_color
        private var lessonType: TextView = itemView.schedule_lessonType
        private var time: TextView = itemView.schedule_lessonTime
        private var name: TextView = itemView.schedule_lessonName
        private var location: TextView = itemView.schedule_lessonLocation
        private var typeDot: ImageView = itemView.schedule_type_dot

        fun bind(lesson: ZajeciaItem?) = with(itemView) {
            val color = getTypeColor(lesson?.typZajec)
            time.text = (lesson?.dataRoz?.substring(11, lesson.dataRoz.length) + " - "
                    + lesson?.dataZak?.substring(11, lesson.dataZak.length))
            name.text = String.format("%s - %s", lesson?.kod, lesson?.nazwa)
            location.text = String.format("%s %s, %s", context.getString(R.string.building), lesson?.budynek, lesson?.nazwaSali)
            leftCornerColor.setBackgroundColor(color)
            typeDot.setColorFilter(color, PorterDuff.Mode.SRC)
            lessonType.text = lesson?.typZajec?.toUpperCase()
            lessonType.setTextColor(color)
        }
    }

}