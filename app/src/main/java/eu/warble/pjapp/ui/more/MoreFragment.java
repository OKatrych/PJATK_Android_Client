package eu.warble.pjapp.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.warble.pjapp.ui.base.BaseFragment;

public class MoreFragment extends BaseFragment<MoreFragmentPresenter> {

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MoreFragmentPresenter createPresenter() {
        return new MoreFragmentPresenter(this);
    }

    @Override
    protected View initView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        return null;
    }
}
