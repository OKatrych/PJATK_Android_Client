package eu.warble.pjapp.ui.student_info.marks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;
import eu.warble.pjapp.ui.list.ListActivity;

public class StudentMarksFragment extends BaseFragment<StudentMarksPresenter>{

    private TextView marks_subject1, marks_type1, marks_mark1, marks_subject2, marks_type2, marks_mark2,
             average_year, average_studies;

    public static StudentMarksFragment newInstance(){
        StudentMarksFragment fragment = new StudentMarksFragment();
        return fragment;
    }

    @Override
    protected StudentMarksPresenter createPresenter() {
        return new StudentMarksPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_marks, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        marks_subject1 = view.findViewById(R.id.marks_subject1);
        marks_type1 = view.findViewById(R.id.marks_type1);
        marks_mark1 = view.findViewById(R.id.marks_mark1);
        marks_subject2 = view.findViewById(R.id.marks_subject2);
        marks_type2 = view.findViewById(R.id.marks_type2);
        marks_mark2 = view.findViewById(R.id.marks_mark2);
        average_year = view.findViewById(R.id.average_year);
        average_studies = view.findViewById(R.id.average_studies);

        view.findViewById(R.id.btn_marks_history).setOnClickListener(l -> startListActivity());
    }

    void startListActivity(){
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("key", "marks");
        startActivity(intent);
    }

    public void setMarks_subject1(String marks_subject1) {
        this.marks_subject1.setText(marks_subject1);
    }

    public void setMarks_type1(String marks_type1) {
        this.marks_type1.setText(marks_type1);
    }

    public void setMarks_mark1(String marks_mark1) {
        this.marks_mark1.setText(marks_mark1);
    }

    public void setMarks_subject2(String marks_subject2) {
        this.marks_subject2.setText(marks_subject2);
    }

    public void setMarks_type2(String marks_type2) {
        this.marks_type2.setText(marks_type2);
    }

    public void setMarks_mark2(String marks_mark2) {
        this.marks_mark2.setText(marks_mark2);
    }

    public void setAverage_year(String average_year) {
        this.average_year.setText(average_year);
    }

    public void setAverage_studies(String average_studies) {
        this.average_studies.setText(average_studies);
    }
}
