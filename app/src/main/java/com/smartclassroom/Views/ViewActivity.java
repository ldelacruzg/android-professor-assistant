package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smartclassroom.Adapters.ViewPagerAdapter;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Views.Fragments.SubjectsFragment;

public class ViewActivity extends AppCompatActivity {
    TabLayout tabLayoutTop;
    TabItem tabItemSubjects, tabItemDevices;
    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerAdapter;

    Teacher teacher;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        initComponents();

        // Se recupera el profesor logueado
        bundle = getIntent().getExtras();
        if (bundle != null)
            teacher = Global.GSON_INSTANCE.fromJson(bundle.getString("teacher"), Teacher.class);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayoutTop.getTabCount());
        viewPagerMain.setAdapter(viewPagerAdapter);
        viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutTop));

        tabLayoutTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMain.setCurrentItem(tab.getPosition());
                Toast.makeText(ViewActivity.this, "position = " + tab.getPosition(), Toast.LENGTH_SHORT).show();
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