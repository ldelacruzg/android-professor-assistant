package com.smartclassroom.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.smartclassroom.Views.Fragments.DevicesFragment;
import com.smartclassroom.Views.Fragments.SubjectsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int numTabItem;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int numTabItem) {
        super(fm, numTabItem);
        this.numTabItem = numTabItem;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SubjectsFragment();
            case 1: return new DevicesFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numTabItem;
    }
}
