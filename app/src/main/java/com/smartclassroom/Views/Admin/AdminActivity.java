package com.smartclassroom.Views.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.smartclassroom.Adapters.ViewPagerAdapter;
import com.smartclassroom.R;
import com.smartclassroom.Views.Admin.Fragments.FingerprintFragment;
import com.smartclassroom.Views.Admin.Fragments.SettingFragment;
import com.smartclassroom.Views.ProfileActivity;

public class AdminActivity extends AppCompatActivity {
    TabLayout tabLayoutTop;
    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initComponents();

        buildViewPagerAdapter();
    }

    private void initComponents() {
        tabLayoutTop = findViewById(R.id.tabLayoutTop);
        viewPagerMain = findViewById(R.id.viewPagerMain);
    }

    private void buildViewPagerAdapter() {
        tabLayoutTop.setupWithViewPager(viewPagerMain);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(new SettingFragment(), "Setting");
        viewPagerAdapter.addFragment(new FingerprintFragment(), "Fingerprint");

        viewPagerMain.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemProfile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}