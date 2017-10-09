package eu.warble.pjapp.ui.main.ftp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jcraft.jsch.ChannelSftp;

import java.util.Vector;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseFragment;

public class FtpFragment extends BaseFragment<FtpPresenter> implements SearchView.OnQueryTextListener{

    private ListView listView;
    private LinearLayout loadingScreen;
    private SearchView searchView;
    private MenuItem searchItem;

    public static FtpFragment newInstance(){
        FtpFragment ftpFragment = new FtpFragment();
        ftpFragment.tag = "fragment_ftp";
        return ftpFragment;
    }

    @Override
    protected FtpPresenter createPresenter() {
        return new FtpPresenter(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ftp, container, false);
        setHasOptionsMenu(true);
        initToolbar();
        initViews(view);
        return view;
    }

    private void initViews(View view){
        loadingScreen = view.findViewById(R.id.loading_screen);
        listView = view.findViewById(R.id.ftpListView);
        listView.setAdapter(new FtpListAdapter(context));
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            ChannelSftp.LsEntry item = (ChannelSftp.LsEntry) listView.getAdapter().getItem(position);
            if(item.getAttrs().isDir()){
                presenter.loadDirectory(item.getFilename());
                if(!searchView.isIconified()){
                    searchItem.collapseActionView();
                }
            }else {
                presenter.downloadFile(item.getFilename());
            }
        });
    }

    public void showLoadingScreen(){
        loadingScreen.setVisibility(View.VISIBLE);
    }

    public void hideLoadingScreen(){
        loadingScreen.setVisibility(View.GONE);
    }

    public void updateList(Vector<ChannelSftp.LsEntry> newData){
        FtpListAdapter adapter = (FtpListAdapter) listView.getAdapter();
        adapter.updateList(newData);
    }

    public void showMessage(String message){
        FragmentActivity activity = getActivity();
        if (activity != null)
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    private void initToolbar(){
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.pjatk);
            bar.setElevation(14f);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        if (loadingScreen.getVisibility() == View.GONE)
            ((FtpListAdapter)listView.getAdapter()).getFilter().filter(newText);
        return true;
    }
}
