package eu.warble.pjapp.ui.student_info;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;

public class StudentInfoFragment extends BaseFragment<StudentInfoPresenter>{

    public static StudentInfoFragment newInstance(){
        StudentInfoFragment fragment = new StudentInfoFragment();
        fragment.tag = "fragment_student";
        return fragment;
    }

    @Override
    protected StudentInfoPresenter createPresenter() {
        return new StudentInfoPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        setHasOptionsMenu(true);
        initTabs(view);
        return view;
    }

    private void initTabs(View parent) {
        ViewPager viewPager = parent.findViewById(R.id.viewPager);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(context, getChildFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = parent.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }
}
