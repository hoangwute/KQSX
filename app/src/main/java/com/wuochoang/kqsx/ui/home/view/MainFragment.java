package com.wuochoang.kqsx.ui.home.view;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.wuochoang.kqsx.R;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.base.BasePresenter;
import com.wuochoang.kqsx.common.view.LockableViewPager;
import com.wuochoang.kqsx.manager.StackManager;
import com.wuochoang.kqsx.ui.home.MainVPAdapter;
import com.wuochoang.kqsx.ui.home.tabs.BaseTabFragment;
import com.wuochoang.kqsx.ui.result.view.ResultFragment;

import butterknife.BindView;

/**
 * Created by admin on 05-Mar-18.
 */

public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.pager)
    LockableViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.mainView)
    LinearLayout mainView;

    private MainVPAdapter mAdapter;
    private StackManager manager;


    @Override
    public void injectDependence() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView() {
        pager.setAdapter(mAdapter = new MainVPAdapter(getChildFragmentManager(), getContext()));
        pager.setSwipeLocked(true);
        pager.setOffscreenPageLimit(1);
        pager.setOnPageChangeListener(this);
        tabLayout.setupWithViewPager(pager);
        //initStack
        manager = new StackManager();
    }

    @Override
    public void initData() {

    }

    public void setCurrentItem(int position) {
        pager.setCurrentItem(position, true);
    }


    public Fragment getCurrentFragment() {
        return mAdapter.getItem(pager.getCurrentItem());
    }

    public FragmentManager getCurrentFragmentMgr() {
        // getChildFragmentManager của từng tab
        return getCurrentFragment().getChildFragmentManager();
    }

    //adding fragment to backstack purpose
    public BaseFragment getFragmentInTop() {
        if (getCurrentFragment() == null || !(getCurrentFragment() instanceof BaseTabFragment))
            return null;
        Fragment fragment = ((BaseTabFragment) getCurrentFragment()).getChildFragmentManager().findFragmentById(R.id.tab_container);
        if (!(fragment instanceof BaseFragment)) return null;
        return (BaseFragment) fragment;
    }

    public boolean onBackPress() {
        int countCurrent = getCurrentFragmentMgr().getBackStackEntryCount();
        if (countCurrent > 0) {
            getCurrentFragmentMgr().popBackStack();
            changeFitWindow(-1);
            return true;
        } else if (!manager.isEmpty()) {
            int tabBefore = manager.getTabBefore();
            if (tabBefore != -1) {
                setCurrentItem(tabBefore);
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    public void changeFitWindow(int position) {
        if (position < 0)
            position = pager.getCurrentItem();
        boolean isFit = position == 0;
        if (isFit) {
            BaseFragment frag = getFragmentInTop();
            if (frag != null) {
                isFit = frag.isShowFitWindows();
            }
        }
        //TODO Fix
        isFit = false;
        mainView.setFitsSystemWindows(!isFit);
        if (getView() != null)
            getView().setFitsSystemWindows(!isFit);
        mainView.requestFitSystemWindows();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!isFit) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeFitWindow(position);
        getCurrentFragment().onResume();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
