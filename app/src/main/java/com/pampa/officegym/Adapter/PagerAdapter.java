package com.pampa.officegym.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pampa.officegym.Fragments.PhotoFragment;
import com.pampa.officegym.Fragments.TextFragment;
import com.pampa.officegym.Fragments.VideoFragment;

/**
 * Created by adrian on 28/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new VideoFragment();
            case 1:
                return new PhotoFragment();
            case 2:
                return new TextFragment();
            default:
                return new VideoFragment();
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
