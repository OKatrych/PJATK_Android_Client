package eu.warble.pjapp.ui.student_info.about;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.List;

import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource.LoadStudentDataCallback;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.model.StudiaItem;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;

public class StudentAboutPresenter extends BaseFragmentPresenter<StudentAboutFragment>{

    public StudentAboutPresenter(StudentAboutFragment fragment) {
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
        repository.getStudentData(new LoadStudentDataCallback() {
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
        fragment.setStudentName(String.format("%s %s", studentData.getImie(), studentData.getNazwisko()));
        List<StudiaItem> studies = studentData.getStudia();
        if (!studies.isEmpty()) {
            fragment.setStudies(studies.get(0).getKierunek().getNazwaKierunekAng());
            fragment.setYear(String.valueOf(studies.get(0).getRokStudiow()));
            fragment.setSemester(String.valueOf(studies.get(0).getSemestrStudiow()));
            fragment.setAvgMark(BigDecimal.valueOf(studies.get(0).getSredniaStudia()).floatValue());
            fragment.setSpecialization(studies.get(0).getSpecjalizacja());
        }
        fragment.setGroups(TextUtils.join(", ", studentData.getGrupy()));
        fragment.setStatus(studentData.getStatus());
    }
}
