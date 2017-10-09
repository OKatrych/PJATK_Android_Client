package eu.warble.pjapp.ui.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;
import eu.warble.pjapp.ui.base.BaseFragment;
import eu.warble.pjapp.ui.login.LoginActivity;
import eu.warble.pjapp.ui.main.ftp.FtpFragment;
import eu.warble.pjapp.ui.main.schedule.ScheduleFragment;
import eu.warble.pjapp.ui.main.student_info.StudentInfoFragment;

public class MainActivity extends BaseActivity<MainPresenter> {

    private int selectedNavigationItem;
    private View contentView;
    private BaseFragment[] fragments;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        contentView = findViewById(android.R.id.content);
        initFragments();
        initBottomNavigationView();

        if (savedInstanceState != null)
            selectedNavigationItem = savedInstanceState.getInt("selectedNavigationItem");
        else
            selectedNavigationItem = 0;
        selectBottomNavItem(selectedNavigationItem);

        checkPermissions();
    }

    private void initFragments() {
        fragments = new BaseFragment[]{
                StudentInfoFragment.newInstance(),
                ScheduleFragment.newInstance(),
                FtpFragment.newInstance()
        };
    }

    private void initBottomNavigationView() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(l ->{
            updateActionBar(l.getItemId());
            return selectBottomNavItem(getPosition(l.getItemId()));
        });
    }

    private boolean selectBottomNavItem(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String tag = fragments[position].tag;

        if (fragmentManager.findFragmentByTag(tag) == null)
            transaction.add(R.id.main_content, fragments[position], tag);

        transaction.hide(fragments[selectedNavigationItem]);
        transaction.show(fragments[position]);
        transaction.commit();

        selectedNavigationItem = position;
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
                case R.id.navigation_ftp:
                    bar.setElevation(14f);
                    break;
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
            case R.id.navigation_ftp:
                return 2;
            default:
                return -1;
        }
    }

    private void checkPermissions() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new CompositeMultiplePermissionsListener(
                                new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {}

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                        showPermissionRationale(permissionToken);
                                    }
                                },
                                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                        .with((ViewGroup) contentView, R.string.all_permissions_denied_feedback)
                                        .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                                        .withDuration(Snackbar.LENGTH_INDEFINITE)
                                        .build()
                        )
                ).check();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    token.cancelPermissionRequest();
                })
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                    token.continuePermissionRequest();
                })
                .setOnDismissListener(dialog -> token.cancelPermissionRequest())
                .show();
    }
}