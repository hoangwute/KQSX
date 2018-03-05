package com.wuochoang.kqsx.ui.home;

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
    private Tab3Fragment tab3Fragment;
    private Tab4Fragment tab4Fragment;
    private Tab5Fragment tab5Fragment;

    public MainVPAdapter(FragmentManager fm) {
        super(fm);
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
            case 2:
                if (tab3Fragment == null) {
                    tab3Fragment = new Tab3Fragment();
                }
                return tab3Fragment;
            case 3:
                if (tab4Fragment == null) {
                    tab4Fragment = new Tab4Fragment();
                }
                return tab4Fragment;
            case 4:
                if (tab5Fragment == null) {
                    tab5Fragment = new Tab5Fragment();
                }
                return tab5Fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
