package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smartclassroom.Adapters.ViewPagerAdapter;
import com.smartclassroom.R;

public class ViewActivity extends AppCompatActivity {
    TabLayout tabLayoutTop;
    TabItem tabItemSubjects, tabItemDevices;
    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        initComponents();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayoutTop.getTabCount());
        viewPagerMain.setAdapter(viewPagerAdapter);
        viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutTop));

        tabLayoutTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initComponents() {
        tabLayoutTop = findViewById(R.id.tabLayoutTop);
        tabItemDevices = findViewById(R.id.tabItemDevices);
        tabItemSubjects = findViewById(R.id.tabItemSubjects);
        viewPagerMain = findViewById(R.id.viewPagerMain);
    }
}