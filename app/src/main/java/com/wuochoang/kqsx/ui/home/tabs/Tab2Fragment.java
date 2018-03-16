package com.wuochoang.kqsx.ui.home.tabs;

import com.wuochoang.kqsx.base.BaseFragment;
import com.wuochoang.kqsx.ui.devices.DevicesFragment;
import com.wuochoang.kqsx.ui.history.view.HistoryFragment;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class Tab2Fragment extends BaseTabFragment {
    @Override
    public BaseFragment initFragment() {
        return new HistoryFragment();
    }

}
