package eu.warble.pjapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseActivityPresenter <T extends BaseActivity> {

    protected T activity;

    protected abstract void initPresenter(@Nullable Bundle savedInstanceState);

    public BaseActivityPresenter(T activity) {
        this.activity = activity;
    }

    protected void onDestroyActivity() {
        if (activity != null) {
            activity = null;
        }
    }
}
