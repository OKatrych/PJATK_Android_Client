package eu.warble.pjapp.ui.student_info.marks;


import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.OcenyItem;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.model.StudiaItem;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class StudentMarksPresenter extends BaseFragmentPresenter<StudentMarksFragment>{

    public StudentMarksPresenter(StudentMarksFragment fragment) {
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

    private void fillViews(Student studentData) {
        fillAverageMarksCard(studentData);
        fillMarksCard(studentData);
    }

    private void fillMarksCard(Student studentData) {
        List<OcenyItem> marks = studentData.getOceny();
        if (!marks.isEmpty()){
            OcenyItem mark = marks.get(0);
            String type = mark.getZaliczenie().toLowerCase()
                    .equals("egzamin") ? fragment.getString(R.string.exam) : fragment.getString(R.string.pass);
            fragment.setMarks_subject1(mark.getKodPrzedmiotu());
            fragment.setMarks_type1(type);
            fragment.setMarks_mark1(mark.getOcena());
            if (marks.size() >= 2){
                OcenyItem mark1 = marks.get(1);
                String type1 = mark1.getZaliczenie().toLowerCase()
                        .equals("egzamin") ? fragment.getString(R.string.exam) : fragment.getString(R.string.pass);
                fragment.setMarks_subject2(mark1.getKodPrzedmiotu());
                fragment.setMarks_type2(type1);
                fragment.setMarks_mark2(mark1.getOcena());
            }
        }
    }

    private void fillAverageMarksCard(Student studentData) {
        StudiaItem studiaItem = studentData.getStudia().isEmpty() ? null : studentData.getStudia().get(0);
        if (studiaItem != null) {
            fragment.setAverage_year(String.valueOf(studiaItem.getSredniaRok()));
            fragment.setAverage_studies(String.valueOf(studiaItem.getSredniaStudia()));
        }
    }
}
