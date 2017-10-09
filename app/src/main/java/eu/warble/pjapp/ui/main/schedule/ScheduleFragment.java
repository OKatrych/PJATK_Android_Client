package eu.warble.pjapp.ui.main.schedule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.leakcanary.RefWatcher;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;
import solar.blaz.date.week.WeekDatePicker;

public class ScheduleFragment extends BaseFragment<SchedulePresenter> {

    ListView lessonsList;
    WeekDatePicker datePicker;

    public static ScheduleFragment newInstance(){
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.tag = "fragment_schedule";
        return fragment;
    }

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        setHasOptionsMenu(true);
        initToolbar();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        lessonsList = view.findViewById(R.id.schedule_listView);
        datePicker = view.findViewById(R.id.date_picker);
        lessonsList.setEmptyView(view.findViewById(R.id.empty_lessons_text));
    }

    private void initToolbar(){
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.pjatk);
            bar.setElevation(0f);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }
}
