package eu.warble.pjappkotlin.mvp.studentinfo.fees.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.OplatyItem
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_receivables.view.receivable_item_amount
import kotlinx.android.synthetic.main.list_item_receivables.view.receivable_item_date
import kotlinx.android.synthetic.main.list_item_receivables.view.receivable_item_instalment
import kotlinx.android.synthetic.main.list_item_receivables.view.receivable_item_name
import kotlinx.android.synthetic.main.list_item_receivables.view.receivable_item_total_instalment
import java.util.Collections

class ReceivablesListAdapter(
        val studentData: Student
) : RecyclerView.Adapter<ReceivablesListAdapter.ViewHolder>() {

    private val payments: List<OplatyItem>? = studentData.oplaty?.apply { Collections.reverse(this) }

    override fun getItemCount(): Int {
        return payments?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(payments?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_receivables))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.receivable_item_name
        private val amount: TextView = itemView.receivable_item_amount
        private val date: TextView = itemView.receivable_item_date
        private val instalment: TextView = itemView.receivable_item_instalment
        private val totalInstalment: TextView = itemView.receivable_item_total_instalment

        fun bind(payment: OplatyItem?) = with(itemView) {
            name.text = payment?.nazwa
            amount.text = String.format("%s zł", payment?.kwota)
            date.text = payment?.terminPlatnosci
            instalment.text = payment?.nrRaty.toString()
            totalInstalment.text = payment?.liczbaRat.toString()
        }
    }
}