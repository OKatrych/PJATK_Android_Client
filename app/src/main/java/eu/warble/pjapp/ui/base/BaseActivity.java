package eu.warble.pjapp.ui.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity<T extends BaseActivityPresenter> extends AppCompatActivity{

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        presenter = createPresenter();
        presenter.initPresenter(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroyActivity();
        }
        super.onDestroy();
    }

    protected abstract T createPresenter();

    protected abstract void initView(Bundle savedInstanceState);

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
