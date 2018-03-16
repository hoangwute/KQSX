package com.wuochoang.kqsx.ui.home.tabs;

import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.ui.devices.DevicesFragment;
import com.wuochoang.kqsx.ui.result.view.ResultFragment;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class Tab1Fragment extends BaseTabFragment {
    @Override
    public BaseFragment initFragment() {
        return new ResultFragment();
    }

//
//    @Override
//    public void onBackStackChanged() {
//        Timber.i("Size Tab1 : " + frm.getBackStackEntryCount());
//        Fragment fragment = frm.findFragmentById(R.id.tab_container);
//        if (fragment != null)
//            Timber.i("Fragment Tab1 : " + fragment.getClass().getSimpleName());
//    }
}
