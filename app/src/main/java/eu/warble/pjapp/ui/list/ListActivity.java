package eu.warble.pjapp.ui.list;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import eu.warble.pjapp.R;
import eu.warble.pjapp.ui.base.BaseActivity;

public class ListActivity extends BaseActivity<ListPresenter> {

    private ListView listView;

    @Override
    protected ListPresenter createPresenter() {
        return new ListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_list);
        initActionBar();
        listView = findViewById(R.id.listView);
    }

    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }

    public void setTitle(String title){
        ActionBar bar = getSupportActionBar();
        if (bar != null){
            bar.setTitle(title);
        }
    }

    private void initActionBar(){
        ActionBar bar = getSupportActionBar();
        if (bar != null){
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }
}
