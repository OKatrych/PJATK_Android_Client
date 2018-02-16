package eu.warble.pjappkotlin.mvp.studentinfo.fees

import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BasePresenter
import eu.warble.pjappkotlin.mvp.BaseView


interface StudentFeesContract {

    interface View : BaseView<Presenter> {

        val applicationNavigator: ApplicationNavigator

        fun setBalance(balance: String?)

        fun setBalanceReceivables(balanceReceivables: String?)

        fun setBalancePayments(balancePayments: String?)

        fun setBalanceWithdrawals(balanceWithdrawals: String?)

        fun setPaymentsDate1(paymentsDate1: String?)

        fun setPaymentsDate1Amount(paymentsDate1Amount: String?)

        fun setPaymentsDate2(paymentsDate2: String?)

        fun setPaymentsDate2Amount(paymentsDate2Amount: String?)

        fun setReceivablesItem1(receivablesItem1: String?)

        fun setReceivablesItem1Date(receivablesItem1Date: String?)

        fun setReceivablesItem1Amount(receivablesItem1Amount: String?)

        fun setReceivablesItem2(receivablesItem2: String?)

        fun setReceivablesItem2Date(receivablesItem2Date: String?)

        fun setReceivablesItem2Amount(receivablesItem2Amount: String?)

        fun setAccount(account: String?)
    }

    interface Presenter : BasePresenter {
        //no-op
    }

}