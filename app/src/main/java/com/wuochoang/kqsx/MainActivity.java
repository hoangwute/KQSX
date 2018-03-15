package com.wuochoang.kqsx;

import android.os.Bundle;

import com.wuochoang.kqsx.base.BaseActivity;
import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.ui.home.view.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    public BaseFragment initFragment() {
        return new MainFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
