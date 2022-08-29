package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartclassroom.Adapters.AttendancesListItemAdapater;
import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.R;

import java.util.ArrayList;

public class AttendancesActivity extends AppCompatActivity {
    TextView textViewSubjectName;

    RecyclerView recyclerViewAttendances;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Bundle bundle;
    Gson gson = new Gson();
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        initComponents();
    }

    private void initComponents() {
        textViewSubjectName = findViewById(R.id.textViewSubjectNameInAttendance);
        recyclerViewAttendances = findViewById(R.id.recyclerViewAttendances);
    }

    private void bindData() {
        // Recover Subject from Intent
        bundle = getIntent().getExtras();
        subject = gson.fromJson(bundle.getString("subject"), Subject.class);

        // Set text Subject name
        textViewSubjectName.setText(subject.getName());

        // Build recycler view
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new AttendancesListItemAdapater(
                android.R.layout.simple_list_item_activated_2,
                new ArrayList<Attendance>(),
                new AttendancesListItemAdapater.OnItemClickListener() {
                    @Override
                    public void onItemClick(Attendance attendance, int position) {

                    }
                }
        );
        recyclerViewAttendances.setAdapter(adapter);
        recyclerViewAttendances.setLayoutManager(layoutManager);
    }
}