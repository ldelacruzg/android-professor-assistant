package com.smartclassroom.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.smartclassroom.Adapters.AttendanceDetailsItemApapter;
import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.Models.Schedule;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDetailsActivity extends AppCompatActivity {
    TextView textViewSubjectNameInAttendanceDetails, textViewAttendanceDate;
    RecyclerView recyclerViewStudentAttendance;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    LinearProgressIndicator progressIndicator;

    Bundle bundle;
    Attendance attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);


        // init components
        initComponents();

        // bind data
        bindData();

        // request
        requestStudentAttendance();
    }

    private void initComponents() {
        textViewSubjectNameInAttendanceDetails = findViewById(R.id.textViewSubjectNameInAttendanceDetails);
        textViewAttendanceDate = findViewById(R.id.textViewAttendanceDate);
        recyclerViewStudentAttendance = findViewById(R.id.recyclerViewStudentAttendance);
        progressIndicator = findViewById(R.id.progressIndicator);

        bundle = getIntent().getExtras();
        attendance = Global.GSON_INSTANCE.fromJson(bundle.getString("attendance"), Attendance.class);
    }

    private void bindData() {
        textViewSubjectNameInAttendanceDetails.setText(Global.SELECTED_SUBJECT.getName());
        textViewAttendanceDate.setText("Fecha: " + attendance.getOnlyDate() + " Hora: " + attendance.getOnlyTime());
    }

    private void buildRecyclerView(List<AttendanceDetail> attendanceDetails) {
        layoutManager = new LinearLayoutManager(this);
        // adapter
        adapter = new AttendanceDetailsItemApapter(attendanceDetails, R.layout.student_attendance_item, verifyAttendanceChange());
        // set adapter
        recyclerViewStudentAttendance.setAdapter(adapter);
        // set layout
        recyclerViewStudentAttendance.setLayoutManager(layoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean verifyAttendanceChange() {
        // verificar si se puede modificar la asistencia
        if (Global.SELECTED_SUBJECT.getSchedules().size() > 0) {
            //DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
            LocalDateTime currentDateTime = Schedule.getCurrentDateTime();
            DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
            Schedule schedule = null;

            for (Schedule el : Global.SELECTED_SUBJECT.getSchedules()) {
                if (el.getLdtDate().getDayOfWeek().equals(dayOfWeek)) {
                    schedule = el;
                    break;
                }
            }

            return schedule != null && schedule.isEditable();
        }
        return false;
    }

    private void requestStudentAttendance() {
        progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
        Call<List<AttendanceDetail>> studentAttendances = RetrofitManager
                .getSmartClassroomService()
                .getStudentAttendances(Global.SELECTED_SUBJECT.getId(), attendance.getOnlyDate());

        studentAttendances.enqueue(new Callback<List<AttendanceDetail>>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<List<AttendanceDetail>> call, Response<List<AttendanceDetail>> response) {
                List<AttendanceDetail> attendanceDetails = response.body();
                if (attendanceDetails.size() > 0) {
                    attendanceDetails.sort((e1,e2)-> e1.getFullName().compareTo(e2.getFullName()));
                    buildRecyclerView(attendanceDetails);
                }
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<AttendanceDetail>> call, Throwable t) {

            }
        });
    }
}