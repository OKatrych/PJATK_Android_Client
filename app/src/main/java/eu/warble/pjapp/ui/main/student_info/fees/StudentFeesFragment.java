package eu.warble.pjapp.ui.main.student_info.fees;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;
import eu.warble.pjapp.ui.list.ListActivity;

public class StudentFeesFragment extends BaseFragment<StudentFeesPresenter> {

    private TextView balance, balanceReceivables, balancePayments, balanceWithdrawals;
    private TextView paymentsDate1, paymentsDate1Amount, paymentsDate2, paymentsDate2Amount;
    private TextView receivables_item1, receivables_item1_date, receivables_item1_amount, receivables_item2,
             receivables_item2_date, receivables_item2_amount;
    private TextView account;

    public static StudentFeesFragment newInstance(){
        StudentFeesFragment fragment = new StudentFeesFragment();
        return fragment;
    }

    @Override
    protected StudentFeesPresenter createPresenter() {
        return new StudentFeesPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_fees, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View parent) {
        //balance card
        balanceReceivables = parent.findViewById(R.id.balance_receivables);
        balancePayments = parent.findViewById(R.id.balance_payments);
        balanceWithdrawals = parent.findViewById(R.id.balance_withdrawals);
        balance = parent.findViewById(R.id.balance);
        //payments card
        paymentsDate1 = parent.findViewById(R.id.payments_date1);
        paymentsDate1Amount = parent.findViewById(R.id.payments_date1_amount);
        paymentsDate2 = parent.findViewById(R.id.payments_date2);
        paymentsDate2Amount = parent.findViewById(R.id.payments_date2_amount);
        parent.findViewById(R.id.btn_payments_history).setOnClickListener(l -> startListActivity("payment"));
        //receivables card
        receivables_item1 = parent.findViewById(R.id.receivables_item1);
        receivables_item1_date = parent.findViewById(R.id.receivables_item1_date);
        receivables_item1_amount = parent.findViewById(R.id.receivables_item1_amount);
        receivables_item2 = parent.findViewById(R.id.receivables_item2);
        receivables_item2_date = parent.findViewById(R.id.receivables_item2_date);
        receivables_item2_amount = parent.findViewById(R.id.receivables_item2_amount);
        parent.findViewById(R.id.btn_receivables_history).setOnClickListener(l -> startListActivity("receivable"));
        //account card
        account = parent.findViewById(R.id.account);
    }

    private void startListActivity(String key){
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    public void setBalance(String balance) {
        this.balance.setText(balance);
    }

    public void setBalanceReceivables(String balanceReceivables) {
        this.balanceReceivables.setText(balanceReceivables);
    }

    public void setBalancePayments(String balancePayments) {
        this.balancePayments.setText(balancePayments);
    }

    public void setBalanceWithdrawals(String balanceWithdrawals) {
        this.balanceWithdrawals.setText(balanceWithdrawals);
    }

    public void setPaymentsDate1(String paymentsDate1) {
        this.paymentsDate1.setText(paymentsDate1);
    }

    public void setPaymentsDate1Amount(String paymentsDate1Amount) {
        this.paymentsDate1Amount.setText(paymentsDate1Amount);
    }

    public void setPaymentsDate2(String paymentsDate2) {
        this.paymentsDate2.setText(paymentsDate2);
    }

    public void setPaymentsDate2Amount(String paymentsDate2Amount) {
        this.paymentsDate2Amount.setText(paymentsDate2Amount);
    }

    public void setReceivables_item1(String receivables_item1) {
        this.receivables_item1.setText(receivables_item1);
    }

    public void setReceivables_item1_date(String receivables_item1_date) {
        this.receivables_item1_date.setText(receivables_item1_date);
    }

    public void setReceivables_item1_amount(String receivables_item1_amount) {
        this.receivables_item1_amount.setText(receivables_item1_amount);
    }

    public void setReceivables_item2(String receivables_item2) {
        this.receivables_item2.setText(receivables_item2);
    }

    public void setReceivables_item2_date(String receivables_item2_date) {
        this.receivables_item2_date.setText(receivables_item2_date);
    }

    public void setReceivables_item2_amount(String receivables_item2_amount) {
        this.receivables_item2_amount.setText(receivables_item2_amount);
    }

    public void setAccount(String account) {
        this.account.setText(account);
    }
}