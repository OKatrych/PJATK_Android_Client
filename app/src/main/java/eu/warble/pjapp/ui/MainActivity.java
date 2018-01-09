package eu.warble.pjapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.squareup.leakcanary.RefWatcher;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;
import eu.warble.pjapp.ui.base.BaseFragment;
import eu.warble.pjapp.ui.ftp.FtpFragment;
import eu.warble.pjapp.ui.login.LoginActivity;
import eu.warble.pjapp.ui.map.MapListFragment;
import eu.warble.pjapp.ui.schedule.ScheduleFragment;
import eu.warble.pjapp.ui.student_info.StudentInfoFragment;

public class MainActivity extends BaseActivity<MainPresenter> {

    private int selectedNavigationItem;
    private BaseFragment[] fragments;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        initFragments();
        initBottomNavigationView();
        if (savedInstanceState != null)
            selectedNavigationItem = savedInstanceState.getInt("selectedNavigationItem");
        else
            selectedNavigationItem = R.id.navigation_student;
        selectBottomNavItem(selectedNavigationItem);
    }

    private void initFragments() {
        fragments = new BaseFragment[]{
                StudentInfoFragment.newInstance(),
                ScheduleFragment.newInstance(),
                MapListFragment.newInstance(),
                FtpFragment.newInstance()
        };
    }

    private void initBottomNavigationView() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(l -> selectBottomNavItem(l.getItemId()));
    }

    private boolean selectBottomNavItem(int itemId){
        int position = getPosition(itemId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = fragments[position].tag;

        if (fragmentManager.findFragmentByTag(tag) == null)
            transaction.add(R.id.main_content, fragments[position], tag);

        transaction.hide(fragments[getPosition(selectedNavigationItem)]);
        transaction.show(fragments[position]);
        transaction.commit();

        selectedNavigationItem = itemId;
        updateActionBar(itemId);
        return true;
    }

    private void updateActionBar(int fragmentId){
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            switch (fragmentId) {
                case R.id.navigation_student:
                    bar.setElevation(0f);
                    break;
                case R.id.navigation_schedule:
                    bar.setElevation(0f);
                    break;
                default:
                    bar.setElevation(14f);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedNavigationItem", selectedNavigationItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_logOut:
                presenter.logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = Application.getRefWatcher(this);
        refWatcher.watch(this);
    }

    private int getPosition(int fragmentId){
        switch (fragmentId) {
            case R.id.navigation_student:
                return 0;
            case R.id.navigation_schedule:
                return 1;
            case R.id.navigation_maps:
                return 2;
            case R.id.navigation_ftp:
                return 3;
            default:
                return -1;
        }
    }

    public void showApiError(@Nullable String error) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("PJATK API Error")
                .setMessage(error == null ? "Some problems with PJATK API Server": error)
                .setNeutralButton("Refresh", (dialogInterface, i) -> {
                    presenter.checkApiResponseForErrors();
                    dialogInterface.dismiss();
                })
                .show();
    }
}