package eu.warble.pjapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.RootFragmentListener;
import com.ncapdevi.fragnav.FragNavController.TransactionListener;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.squareup.leakcanary.RefWatcher;

import eu.warble.pjapp.Application;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;
import eu.warble.pjapp.ui.ftp.FtpFragment;
import eu.warble.pjapp.ui.login.LoginActivity;
import eu.warble.pjapp.ui.map.MapListFragment;
import eu.warble.pjapp.ui.more.MoreFragment;
import eu.warble.pjapp.ui.schedule.ScheduleFragment;
import eu.warble.pjapp.ui.student_info.StudentInfoFragment;

public class MainActivity extends BaseActivity<MainPresenter>
        implements RootFragmentListener, TransactionListener {

    private final int INDEX_STUDENT = FragNavController.TAB1;
    private final int INDEX_SCHEDULE = FragNavController.TAB2;
    private final int INDEX_MAP = FragNavController.TAB3;
    private final int INDEX_FTP = FragNavController.TAB4;
    private final int INDEX_MORE = FragNavController.TAB5;

    private BottomNavigationView navigationView;
    int selectedNavigationItem;
    private FragNavController fragNavController;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        final boolean initial = savedInstanceState == null;
        navigationView = findViewById(R.id.bottomNavigationView);
        if (initial) {
            selectNavigationItem(R.id.navigation_student);
        }
        initFragNavController(savedInstanceState);
    }

    private void initFragNavController(Bundle savedInstanceState) {
        fragNavController = FragNavController.newBuilder(
                savedInstanceState,
                getSupportFragmentManager(),
                R.id.main_content
        )
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .switchController((i, fragNavTransactionOptions) -> navigationView.setSelectedItemId(i))
                .build();
        fragNavController.executePendingTransactions();
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if (presenter.checkFragmentAccessible(item.getItemId())) {
                fragNavController.switchTab(getPosition(item.getItemId()));
                return true;
            }
            startLoginActivity();
            return false;
        });
        navigationView.setOnNavigationItemReselectedListener(item -> fragNavController.clearStack());
    }

    void selectNavigationItem(int itemId) {
        navigationView.setSelectedItemId(itemId);
    }

    @Override
    public void onTabTransaction(
            @Nullable final Fragment fragment,
            final int index
    ) {
        updateActionBar(index);
    }

    @Override
    public void onFragmentTransaction(
            final Fragment fragment,
            final FragNavController.TransactionType transactionType
    ) {
        //no-op
    }

    @Override
    public Fragment getRootFragment(final int index) {
        switch (index) {
            case INDEX_STUDENT:
                return StudentInfoFragment.newInstance();
            case INDEX_SCHEDULE:
                return ScheduleFragment.newInstance();
            case INDEX_MAP:
                return MapListFragment.newInstance();
            case INDEX_FTP:
                return FtpFragment.newInstance();
            case INDEX_MORE:
                return MoreFragment.newInstance();
            default:
                throw new IllegalStateException("Need to send an index that we know");
        }
    }

    private void updateActionBar(int index) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            if (index == INDEX_STUDENT || index == INDEX_SCHEDULE) {
                bar.setElevation(0f);
            } else {
                bar.setElevation(14f);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_logOut:
                presenter.logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!fragNavController.popFragment()) {
            super.onBackPressed();
        }
    }

    private int getPosition(int fragmentId) {
        switch (fragmentId) {
            case R.id.navigation_student:
                return INDEX_STUDENT;
            case R.id.navigation_schedule:
                return INDEX_SCHEDULE;
            case R.id.navigation_maps:
                return INDEX_MAP;
            case R.id.navigation_ftp:
                return INDEX_FTP;
            case R.id.navigation_more:
                return INDEX_MORE;
            default:
                return -1;
        }
    }

    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = Application.getRefWatcher(this);
        refWatcher.watch(this);
    }

    public void showApiError(@Nullable String error) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("PJATK API Error")
                .setMessage(error == null ? "Some problems with PJATK API Server" : error)
                .setNeutralButton("Refresh", (dialogInterface, i) -> {
                    presenter.checkApiResponseForErrors();
                    dialogInterface.dismiss();
                })
                .show();
    }
}