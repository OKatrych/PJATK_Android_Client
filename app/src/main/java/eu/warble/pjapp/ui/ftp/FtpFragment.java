package eu.warble.pjapp.ui.ftp;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;


import eu.warble.getter.model.GetterFile;
import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

public class FtpFragment extends BaseFragment<FtpPresenter> implements SearchView.OnQueryTextListener {

    private ListView listView;
    private LinearLayout loadingScreen;
    private SearchView searchView;
    private MenuItem searchItem;

    public static FtpFragment newInstance() {
        FtpFragment ftpFragment = new FtpFragment();
        ftpFragment.tag = "fragment_ftp";
        return ftpFragment;
    }

    @Override
    protected FtpPresenter createPresenter() {
        return new FtpPresenter(this);
    }

    @Override
    protected View initView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_ftp, container, false);
        setHasOptionsMenu(true);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        loadingScreen = view.findViewById(R.id.loading_screen);
        listView = view.findViewById(R.id.ftpListView);
        listView.setAdapter(new FtpListAdapter(context));
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            GetterFile item = (GetterFile) listView.getAdapter().getItem(position);
            if (item.isDirectory()) {
                presenter.loadDirectory(item.getName());
                if (!searchView.isIconified()) {
                    searchItem.collapseActionView();
                }
            } else {
                FtpFragmentPermissionsDispatcher.downloadFileWithPermissionCheck(FtpFragment.this, item);
            }
        });
    }

    public void showLoadingScreen() {
        loadingScreen.setVisibility(View.VISIBLE);
    }

    public void hideLoadingScreen() {
        loadingScreen.setVisibility(View.GONE);
    }

    public void updateList(List<GetterFile> newData) {
        FtpListAdapter adapter = (FtpListAdapter) listView.getAdapter();
        adapter.updateList(newData);
    }

    public void showMessage(String message) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void downloadFile(GetterFile file) {
        presenter.downloadFile(file);
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForStorage(final PermissionRequest request) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .setCancelable(false)
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showDeniedForStorage() {
        Toast.makeText(getActivity(), R.string.permissions_declined, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showNeverAskForStorage() {
        Toast.makeText(getActivity(), "Never ask for storage", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu,
            MenuInflater inflater
    ) {
        inflater.inflate(R.menu.toolbar, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setEnabled(true);
        searchItem.setVisible(true);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (loadingScreen.getVisibility() == View.GONE) {
            ((FtpListAdapter) listView.getAdapter()).getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FtpFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
