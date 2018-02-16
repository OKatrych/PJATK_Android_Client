package eu.warble.pjappkotlin.mvp.studentinfo.fees.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.data.model.PlatnosciItem
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_payments.view.payments_list_amount
import kotlinx.android.synthetic.main.list_item_payments.view.payments_list_date
import kotlinx.android.synthetic.main.list_item_payments.view.payments_list_description
import kotlinx.android.synthetic.main.list_item_payments.view.payments_list_name

class PaymentsListAdapter(
        val studentData: Student
) : RecyclerView.Adapter<PaymentsListAdapter.ViewHolder>() {

    private val payments: List<PlatnosciItem>? = studentData.platnosci

    override fun getItemCount(): Int {
        return payments?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(payments?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_payments))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.payments_list_name
        private val amount: TextView = itemView.payments_list_amount
        private val date: TextView = itemView.payments_list_date
        private val description: TextView = itemView.payments_list_description

        fun bind(payment: PlatnosciItem?) = with(itemView) {
            name.text = payment?.wplacajacy
            amount.text = payment?.kwota.toString()
            date.text = payment?.dataWplaty
            description.text = payment?.tytulWplaty
        }
    }

}