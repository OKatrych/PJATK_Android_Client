package eu.warble.pjapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T extends BaseFragmentPresenter> extends Fragment{
    protected T presenter;
    public Context context;
    public String tag;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = initView(inflater, container, savedInstanceState);
        presenter = createPresenter();
        presenter.initPresenter(savedInstanceState);
        return v;
    }

    @Override
    public void onDetach() {
        if (presenter != null) {
            presenter.onDetachFragment();
        }
        super.onDetach();
    }

    public void showError(String error){
        FragmentActivity activity = getActivity();
        if (activity != null)
            Snackbar.make(activity.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
    }

    protected abstract T createPresenter();

    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
