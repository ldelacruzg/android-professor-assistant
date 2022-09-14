package com.smartclassroom.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
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
                        Global.SELECTED_ATTENDANCE = attendance;
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                List<Attendance> attendanceList = response.body();
                if (attendanceList.size() > 0) {
                    attendanceList.sort((t1, t2) -> t1.getOnlyDate().compareTo(t2.getOnlyDate()));
                    buildRecyclerView(attendanceList);
                    requestSubjectRefresh();
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {

            }
        });
    }

    private void requestSubjectRefresh() {
        System.out.println("hola");
        Call<Subject> subjectById = RetrofitManager
                .getSmartClassroomService()
                .getSubjectById(Global.SELECTED_SUBJECT.getId());

        subjectById.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                Global.SELECTED_SUBJECT = response.body();
                System.out.println("Refresh subject" + response.body().getSchedules());
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {

            }
        });
    }
}