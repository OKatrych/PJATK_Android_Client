package eu.warble.pjapp.ui.main.schedule;


import android.os.Bundle;
import android.support.annotation.Nullable;

import org.threeten.bp.LocalDate;

import eu.warble.pjapp.data.StudentDataRepository;
import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.local.StudentLocalDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.remote.PjatkAPI;
import eu.warble.pjapp.ui.base.BaseFragmentPresenter;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.CredentialsManager;
import eu.warble.pjapp.util.ScheduleHelper;
import solar.blaz.date.week.WeekDatePicker;

public class SchedulePresenter extends BaseFragmentPresenter<ScheduleFragment> {

    public SchedulePresenter(ScheduleFragment fragment) {
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
        ScheduleHelper scheduleHelper = new ScheduleHelper(studentData);
        WeekDatePicker datePicker = fragment.datePicker;

        if (studentData.getZajecia().isEmpty()) {
            datePicker.setLimits(scheduleHelper.getFirstDayByDate(), scheduleHelper.getLastDayByDate());
            datePicker.selectDay(scheduleHelper.now());
        }else {
            LocalDate selectDate = selectDate(scheduleHelper);
            datePicker.setLimits(scheduleHelper.getFirstDayDate(), scheduleHelper.getLastDayDate());
            datePicker.selectDay(selectDate);
            ScheduleListAdapter adapter = new ScheduleListAdapter(
                    fragment.context, scheduleHelper.getLessonsForDate(selectDate));
            fragment.lessonsList.setAdapter(adapter);
            datePicker.setOnDateSelectedListener(date -> {
                adapter.updateList(scheduleHelper.getLessonsForDate(date));
                fragment.lessonsList.invalidateViews();
            });
        }
    }

    private LocalDate selectDate(ScheduleHelper calendar){
        if (calendar.existsDay(calendar.now()))
            return calendar.now();
        return calendar.getFirstDayDate();
    }
}
