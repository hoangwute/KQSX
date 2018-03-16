package com.wuochoang.kqsx;

import android.os.Bundle;

import com.wuochoang.kqsx.base.BaseActivity;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.ui.home.view.MainFragment;

public class MainActivity extends BaseActivity {

    public MainFragment mainFragment;


    @Override
    public BaseFragment initFragment() {
        if (mainFragment == null)
            mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeFitWindown() {
        if (mainFragment != null)
            mainFragment.changeFitWindow(-1);
    }

    @Override
    public void onBackPressed() {
        if(mainFragment != null && !mainFragment.onBackPress())
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
