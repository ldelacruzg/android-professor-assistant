package com.smartclassroom.Controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smartclassroom.Views.Fragments.DevicesFragment;
import com.smartclassroom.Views.Fragments.SubjectsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int numTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTabs = behavior;
    }

    @NonNull
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
        return numTabs;
    }
}
