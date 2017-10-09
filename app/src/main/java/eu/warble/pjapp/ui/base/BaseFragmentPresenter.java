package eu.warble.pjapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseFragmentPresenter <T extends BaseFragment> {

    protected T fragment;

    protected abstract void initPresenter(@Nullable Bundle savedInstanceState);

    public BaseFragmentPresenter(T fragment) {
        this.fragment = fragment;
    }

    protected void onDetachFragment() {
        if (fragment != null) {
            fragment = null;
        }
    }
}
