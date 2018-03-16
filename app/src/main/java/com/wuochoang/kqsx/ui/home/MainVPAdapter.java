package com.wuochoang.kqsx.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wuochoang.kqsx.ui.home.tabs.Tab1Fragment;
import com.wuochoang.kqsx.ui.home.tabs.Tab2Fragment;
import com.wuochoang.kqsx.ui.home.tabs.Tab3Fragment;
import com.wuochoang.kqsx.ui.home.tabs.Tab4Fragment;
import com.wuochoang.kqsx.ui.home.tabs.Tab5Fragment;

/**
 * Created by quyenlx on 8/9/2017.
 */

public class MainVPAdapter extends FragmentStatePagerAdapter {
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;

    private Context mContext;

    public MainVPAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (tab1Fragment == null) {
                    tab1Fragment = new Tab1Fragment();
                }
                return tab1Fragment;
            case 1:
                if (tab2Fragment == null) {
                    tab2Fragment = new Tab2Fragment();
                }
                return tab2Fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Kết Quả";
            case 1:
                return "Lịch Sử";
            default:
                return null;
        }
    }
}
