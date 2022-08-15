package com.ronghuaxueleng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ronghuaxueleng.fragment.DakaFragment;
import com.ronghuaxueleng.fragment.DownFileFragment;
import com.ronghuaxueleng.fragment.GETFragment;
import com.ronghuaxueleng.fragment.POSTFragment;
import com.ronghuaxueleng.fragment.ShadowFragemnt;
import com.ronghuaxueleng.fragment.UploadFragment;


/**
 * Created by Administrator on 2018/1/19.
 * 这是多fragment的Adapter
 */

public class ViewPagerFragmentMoreAdapter extends FragmentStatePagerAdapter {

    private final String[] arr;

    public ViewPagerFragmentMoreAdapter(FragmentManager fm, String[] arr) {
        super(fm);
        this.arr = arr;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return DakaFragment.newFragment();
        } else if (position == 1) {
            return GETFragment.newFragment();
        } else if (position == 2) {
            return DownFileFragment.newFragment();
        } else if (position == 3) {
            return POSTFragment.newFragment();
        } else if (position == 4) {
            return UploadFragment.newFragment();
        } else {
            return ShadowFragemnt.newFragment();
        }
    }

    @Override
    public int getCount() {
        return arr != null ? arr.length : 0;
    }
}
