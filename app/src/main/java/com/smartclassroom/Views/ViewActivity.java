package com.smartclassroom.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.smartclassroom.Adapters.ViewPagerAdapter;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.R;
import com.smartclassroom.Views.Dialogs.DialogProfile;
import com.smartclassroom.Views.Fragments.ControlsFragment;
import com.smartclassroom.Views.Fragments.DevicesFragment;
import com.smartclassroom.Views.Fragments.SubjectsFragment;


public class ViewActivity extends AppCompatActivity {
    TabLayout tabLayoutTop;
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

        // Create fragments
        SubjectsFragment subjectsFragment = new SubjectsFragment();
        subjectsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .commit();

        tabLayoutTop.setupWithViewPager(viewPagerMain);

        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        viewPagerAdapter.addFragment(subjectsFragment, "Subjects");
        viewPagerAdapter.addFragment(new DevicesFragment(), "Devices");
        viewPagerAdapter.addFragment(new ControlsFragment(), "Controls");

        viewPagerMain.setAdapter(viewPagerAdapter);
    }

    private void initComponents() {
        tabLayoutTop = findViewById(R.id.tabLayoutTop);
        viewPagerMain = findViewById(R.id.viewPagerMain);
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