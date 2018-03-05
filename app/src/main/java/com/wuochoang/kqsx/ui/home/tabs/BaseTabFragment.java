package com.wuochoang.kqsx.ui.home.tabs;

import android.support.v4.app.FragmentManager;

import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;

import timber.log.Timber;
import com.wuochoang.kqsx.R;

/**
 * Created by quyenlx on 8/9/2017.
 */

public abstract class BaseTabFragment extends BaseFragment implements FragmentManager.OnBackStackChangedListener {
    protected FragmentManager frm;

    public abstract BaseFragment initFragment();

    @Override
    public void injectDependence() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView() {
        frm = getChildFragmentManager();
        frm.addOnBackStackChangedListener(this);
        frm.beginTransaction()
                .replace(R.id.tab_container, initFragment())
                .commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackStackChanged() {
        BaseFragment fragment = (BaseFragment) frm.findFragmentById(R.id.tab_container);
        setTitle(fragment.getTitle());
        Timber.i("Fragment : " + fragment.getClass().getSimpleName());
    }
}
