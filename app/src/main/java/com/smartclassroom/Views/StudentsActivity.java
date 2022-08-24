package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.smartclassroom.R;

public class StudentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}