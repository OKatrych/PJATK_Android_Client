package eu.warble.pjappkotlin.mvp.studentinfo.fees

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.card_account.account
import kotlinx.android.synthetic.main.card_balance.balance
import kotlinx.android.synthetic.main.card_balance.balance_payments
import kotlinx.android.synthetic.main.card_balance.balance_receivables
import kotlinx.android.synthetic.main.card_balance.balance_withdrawals
import kotlinx.android.synthetic.main.card_payments.payments_date1
import kotlinx.android.synthetic.main.card_payments.payments_date1_amount
import kotlinx.android.synthetic.main.card_payments.payments_date2
import kotlinx.android.synthetic.main.card_payments.payments_date2_amount
import kotlinx.android.synthetic.main.card_payments.view.btn_payments_history
import kotlinx.android.synthetic.main.card_receivables.receivables_item1
import kotlinx.android.synthetic.main.card_receivables.receivables_item1_amount
import kotlinx.android.synthetic.main.card_receivables.receivables_item1_date
import kotlinx.android.synthetic.main.card_receivables.receivables_item2
import kotlinx.android.synthetic.main.card_receivables.receivables_item2_amount
import kotlinx.android.synthetic.main.card_receivables.receivables_item2_date
import kotlinx.android.synthetic.main.card_receivables.view.btn_receivables_history

class StudentFeesFragment : BaseFragment(), StudentFeesContract.View{

    override val TAG: String = "fragment_student_fees"

    override lateinit var presenter: StudentFeesContract.Presenter

    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_fees, container, false)
        initPresenter()
        initListeners(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun initListeners(view: View) {
        view.btn_payments_history.setOnClickListener {
            applicationNavigator.goToListActivity(Constants.ListActivityAdapterType.PAYMENT)
        }
        view.btn_receivables_history.setOnClickListener {
            applicationNavigator.goToListActivity(Constants.ListActivityAdapterType.RECEIVABLE)
        }
    }

    private fun initPresenter() {
        presenter = StudentFeesPresenter(
                Injection.provideStudentDataRepository(mContext as Context),
                this,
                mContext as Context
        )
    }

    override fun setBalance(balance: String?) {
        this.balance.text = balance
    }

    override fun setBalanceReceivables(balanceReceivables: String?) {
        this.balance_receivables.text = balanceReceivables
    }

    override fun setBalancePayments(balancePayments: String?) {
        this.balance_payments.text = balancePayments
    }

    override fun setBalanceWithdrawals(balanceWithdrawals: String?) {
        this.balance_withdrawals.text = balanceWithdrawals
    }

    override fun setPaymentsDate1(paymentsDate1: String?) {
        this.payments_date1.text = paymentsDate1
    }

    override fun setPaymentsDate1Amount(paymentsDate1Amount: String?) {
        this.payments_date1_amount.text = paymentsDate1Amount
    }

    override fun setPaymentsDate2(paymentsDate2: String?) {
        this.payments_date2.text = paymentsDate2
    }

    override fun setPaymentsDate2Amount(paymentsDate2Amount: String?) {
        this.payments_date2_amount.text = paymentsDate2Amount
    }

    override fun setReceivablesItem1(receivablesItem1: String?) {
        this.receivables_item1.text = receivablesItem1
    }

    override fun setReceivablesItem1Date(receivablesItem1Date: String?) {
        this.receivables_item1_date.text = receivablesItem1Date
    }

    override fun setReceivablesItem1Amount(receivablesItem1Amount: String?) {
        this.receivables_item1_amount.text = receivablesItem1Amount
    }

    override fun setReceivablesItem2(receivablesItem2: String?) {
        this.receivables_item2.text = receivablesItem2
    }

    override fun setReceivablesItem2Date(receivablesItem2Date: String?) {
        this.receivables_item2_date.text = receivablesItem2Date
    }

    override fun setReceivablesItem2Amount(receivablesItem2Amount: String?) {
        this.receivables_item2_amount.text = receivablesItem2Amount
    }

    override fun setAccount(account: String?) {
        this.account.text = account
    }

    companion object {

        fun newInstance() = StudentFeesFragment()

    }
}