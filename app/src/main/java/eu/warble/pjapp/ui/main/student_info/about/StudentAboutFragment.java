package eu.warble.pjapp.ui.main.student_info.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;


public class StudentAboutFragment extends BaseFragment<StudentAboutPresenter> {

    private TextView studentName, studies, year, semester, groups, status, specialization;
    private RatingBar avgMark;

    public static StudentAboutFragment newInstance(){
        StudentAboutFragment fragment = new StudentAboutFragment();
        return fragment;
    }

    @Override
    protected StudentAboutPresenter createPresenter() {
        return new StudentAboutPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_about, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View parent){
        studentName = parent.findViewById(R.id.student_name);
        studies = parent.findViewById(R.id.studies);
        year = parent.findViewById(R.id.year_counter);
        semester = parent.findViewById(R.id.semester_counter);
        groups = parent.findViewById(R.id.groups);
        status = parent.findViewById(R.id.status);
        avgMark = parent.findViewById(R.id.ratingBar);
        specialization = parent.findViewById(R.id.specialization);
    }

    public void setStudentName(String studentName) {
        this.studentName.setText(studentName);
    }

    public void setStudies(String studies) {
        this.studies.setText(studies);
    }

    public void setYear(String year) {
        this.year.setText(year);
    }

    public void setSemester(String semester) {
        this.semester.setText(semester);
    }

    public void setGroups(String groups) {
        this.groups.setText(groups);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public void setAvgMark(float avgMark) {
        this.avgMark.setRating(avgMark);
    }

    public void setSpecialization(String specialization) {
        this.specialization.setText(
                specialization != null && !specialization.isEmpty() ? specialization : getText(R.string.no)
        );
    }
}