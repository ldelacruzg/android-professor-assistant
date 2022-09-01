package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.smartclassroom.Adapters.AttendancesListItemAdapater;
import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendancesActivity extends AppCompatActivity {
    TextView textViewSubjectName;

    RecyclerView recyclerViewAttendances;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // init components
        initComponents();

        bindData();
    }

    private void initComponents() {
        textViewSubjectName = findViewById(R.id.textViewSubjectNameInAttendance);
        recyclerViewAttendances = findViewById(R.id.recyclerViewAttendances);
        progressIndicator = findViewById(R.id.progressIndicator);
    }

    private void bindData() {
        // Set text Subject name
        textViewSubjectName.setText(Global.SELECTED_SUBJECT.getName());

        // Build recycler view
        requestAttendanceList();
    }

    private void buildRecyclerView(List<Attendance> attendanceList) {
        layoutManager = new LinearLayoutManager(this);
        adapter = new AttendancesListItemAdapater(
                android.R.layout.simple_list_item_activated_1,
                attendanceList,
                new AttendancesListItemAdapater.OnItemClickListener() {
                    @Override
                    public void onItemClick(Attendance attendance, int position) {
                        Intent intent = new Intent(AttendancesActivity.this, AttendanceDetailsActivity.class);
                        intent.putExtra("attendance", Global.GSON_INSTANCE.toJson(attendance));
                        startActivity(intent);
                    }
                }
        );

        recyclerViewAttendances.setAdapter(adapter);
        recyclerViewAttendances.setLayoutManager(layoutManager);
    }

    private void requestAttendanceList() {
        progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
        Call<List<Attendance>> allAttendancesBySubject = RetrofitManager
                .getSmartClassroomService()
                .getAllAttendancesBySubject(Global.SELECTED_SUBJECT.getId());

        allAttendancesBySubject.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                List<Attendance> attendanceList = response.body();
                if (attendanceList.size() > 0) {
                    buildRecyclerView(attendanceList);
                }
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {

            }
        });
    }
}