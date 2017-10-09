package eu.warble.pjapp.ui.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseActivityPresenter;
import eu.warble.pjapp.ui.main.student_info.fees.PaymentsListAdapter;
import eu.warble.pjapp.ui.main.student_info.fees.ReceivablesListAdapter;
import eu.warble.pjapp.ui.main.student_info.marks.MarksListAdapter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class ListPresenter extends BaseActivityPresenter<ListActivity> {

    public ListPresenter(ListActivity activity) {
        super(activity);
    }

    @Override
    protected void initPresenter(@Nullable Bundle savedInstanceState) {
        loadData();
    }

    private void loadData(){
        AppExecutors executors = new AppExecutors();
        StudentDataRepository repository = StudentDataRepository.getInstance(
                PjatkAPI.getInstance(CredentialsManager.getInstance().getCredentials(activity.getApplicationContext()), executors),
                StudentLocalDataSource.getInstance(executors)
        );
        repository.getStudentData(new StudentDataSource.LoadStudentDataCallback() {
            @Override
            public void onDataLoaded(Student studentData) {
                loadAdapter(studentData);
            }

            @Override
            public void onDataNotAvailable(String error) {
                activity.showToast(error);
            }
        });
    }

    private void loadAdapter(Student studentData){
        String key = activity.getIntent().getStringExtra("key");
        if (key == null) key = "finish";
        switch (key){
            case "payment":
                activity.setAdapter(new PaymentsListAdapter(activity.getApplicationContext(), studentData));
                activity.setTitle(R.string.payments);
                break;
            case "receivable":
                activity.setAdapter(new ReceivablesListAdapter(activity.getApplicationContext(), studentData));
                activity.setTitle(R.string.receivables);
                break;
            case "marks":
                activity.setAdapter(new MarksListAdapter(activity.getApplicationContext(), studentData));
                activity.setTitle(R.string.marks);
                break;
            default:
                Log.e("ListActivity", "Wrong Intent KeyExtra");
                activity.finish();
        }
    }
}
