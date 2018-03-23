package com.wuochoang.kqsx.ui.home.tabs;

import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.ui.devices.DevicesFragment;
import com.wuochoang.kqsx.ui.insert.InsertInformationFragment;
import com.wuochoang.kqsx.ui.result.view.ResultFragment;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class Tab3Fragment extends BaseTabFragment {
    @Override
    public BaseFragment initFragment() {
        return new InsertInformationFragment();
    }

//    @Override
//    public void onBackStackChanged() {
//        Timber.i("Size Tab3 : " + frm.getBackStackEntryCount());
//        Fragment fragment = frm.findFragmentById(R.id.tab_container);
//        if (fragment != null)
//            Timber.i("Fragment Tab3 : " + fragment.getClass().getSimpleName());
//    }

}
