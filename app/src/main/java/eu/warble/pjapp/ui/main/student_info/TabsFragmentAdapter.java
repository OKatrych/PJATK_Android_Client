package eu.warble.pjapp.ui.main.student_info;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.main.student_info.about.StudentAboutFragment;
import eu.warble.pjapp.ui.main.student_info.fees.StudentFeesFragment;
import eu.warble.pjapp.ui.main.student_info.marks.StudentMarksFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter{

    private SparseArray<Fragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabsMap();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.tab_student_info);
            case 1:
                return context.getString(R.string.tab_student_fees);
            case 2:
                return context.getString(R.string.tab_student_marks);
            default:
                return "Item";
        }
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap() {
        tabs = new SparseArray<>();
        tabs.put(0, StudentAboutFragment.newInstance());
        tabs.put(1, StudentFeesFragment.newInstance());
        tabs.put(2, StudentMarksFragment.newInstance());
    }
}
