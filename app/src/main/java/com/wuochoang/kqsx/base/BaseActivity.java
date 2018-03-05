package com.wuochoang.kqsx.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wuochoang.kqsx.App;

import com.wuochoang.kqsx.R;

/**
 * Created by quyenlx on 8/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract BaseFragment initFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        replaceFragment(initFragment());
    }

    private void replaceFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.get().setCurrentActivity(this);
    }
}
