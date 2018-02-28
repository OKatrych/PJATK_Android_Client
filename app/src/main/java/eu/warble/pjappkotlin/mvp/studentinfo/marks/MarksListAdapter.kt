package eu.warble.pjappkotlin.mvp.studentinfo.marks

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.OcenyItem
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_marks.view.marks_date
import kotlinx.android.synthetic.main.list_item_marks.view.marks_exam_pass
import kotlinx.android.synthetic.main.list_item_marks.view.marks_mark
import kotlinx.android.synthetic.main.list_item_marks.view.marks_name
import kotlinx.android.synthetic.main.list_item_marks.view.marks_teacher
import java.text.DateFormat
import java.sql.Date

class MarksListAdapter(
val studentData: Student
) : RecyclerView.Adapter<MarksListAdapter.ViewHolder>() {

    private val marks: List<OcenyItem>? = studentData.oceny

    override fun getItemCount(): Int {
        return marks?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marks?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_marks))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mark: TextView = itemView.marks_mark
        private val type: TextView = itemView.marks_exam_pass
        private val date: TextView = itemView.marks_date
        private val lessonCode: TextView = itemView.marks_name
        private val teacher: TextView = itemView.marks_teacher

        fun bind(markItem: OcenyItem?) = with(itemView) {
            mark.text = markItem?.ocena.toString()

            type.text = if (markItem?.zaliczenie?.toLowerCase() == "egzamin")
                context.getString(R.string.exam)
            else
                context.getText(R.string.pass)

            val dateLong = (markItem?.data?.substring(markItem.data.indexOf('(') + 1, markItem.data.indexOf('+')))?.toLong()

            date.text = DateFormat.getDateInstance().format(Date(dateLong ?: 0L))
            lessonCode.text = markItem?.kodPrzedmiotu
            teacher.text = markItem?.prowadzacy
        }
    }

}