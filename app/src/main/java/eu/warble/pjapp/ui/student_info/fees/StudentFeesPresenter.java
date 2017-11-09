package eu.warble.pjapp.ui.student_info.fees;


import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.OplatyItem;
import eu.warble.pjapp.data.model.PlatnosciItem;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class StudentFeesPresenter extends BaseFragmentPresenter<StudentFeesFragment> {

    public StudentFeesPresenter(StudentFeesFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        loadData();
    }

    private void loadData(){
        AppExecutors executors = new AppExecutors();
        StudentDataRepository repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(CredentialsManager.getInstance().getCredentials(fragment.context), executors),
                StudentLocalDataSource.getInstance(executors)
        );
        repository.getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                fillViews(studentData);
            }

            @Override
            public void onDataNotAvailable(String error) {
                fragment.showError(error);
            }
        });
    }

    private void fillViews(Student studentData){
        fillBalance(studentData);
        fillPayments(studentData);
        fillReceivables(studentData);
        fragment.setAccount(studentData.getKontoWplat());
    }

    private void fillBalance(Student studentData) {
        fragment.setBalance(String.valueOf(studentData.getSaldo()));
        fragment.setBalancePayments(String.valueOf(studentData.getKwotaWplat()));
        fragment.setBalanceReceivables(String.valueOf(studentData.getKwotaNaleznosci()));
        fragment.setBalanceWithdrawals(String.valueOf(studentData.getKwotaWyplat()));
    }

    private void fillPayments(Student studentData){
        List<PlatnosciItem> platnosci = studentData.getPlatnosci();
        if (!platnosci.isEmpty()){
            fragment.setPaymentsDate1(platnosci.get(platnosci.size()-1).getDataWplaty());
            fragment.setPaymentsDate1Amount(platnosci.get(platnosci.size()-1).getKwota() + "");
            if (platnosci.size() >=2) {
                fragment.setPaymentsDate2(platnosci.get(platnosci.size() - 2).getDataWplaty());
                fragment.setPaymentsDate2Amount(platnosci.get(platnosci.size() - 2).getKwota() + "");
            }
        }
    }

    private void fillReceivables(Student studentData){
        List<OplatyItem> oplaty = studentData.getOplaty();
        if (!oplaty.isEmpty()){
            fragment.setReceivables_item1(oplaty.get(oplaty.size()-1).getNazwa());
            fragment.setReceivables_item1_date(oplaty.get(oplaty.size()-1).getTerminPlatnosci());
            fragment.setReceivables_item1_amount(oplaty.get(oplaty.size()-1).getKwota() + "");
            if (oplaty.size() >= 2) {
                fragment.setReceivables_item2(oplaty.get(oplaty.size()-2).getNazwa());
                fragment.setReceivables_item2_date(oplaty.get(oplaty.size()-2).getTerminPlatnosci());
                fragment.setReceivables_item2_amount(oplaty.get(oplaty.size()-2).getKwota() + "");
            }
        }
    }
}
