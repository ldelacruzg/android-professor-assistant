package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.smartclassroom.Adapters.AttendanceDetailsItemApapter;
import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.Calendar;
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
        adapter = new AttendanceDetailsItemApapter(attendanceDetails, R.layout.student_attendance_item);
        // set adapter
        recyclerViewStudentAttendance.setAdapter(adapter);
        // set layout
        recyclerViewStudentAttendance.setLayoutManager(layoutManager);
    }

    private void requestStudentAttendance() {
        Call<List<AttendanceDetail>> studentAttendances = RetrofitManager
                .getSmartClassroomService()
                .getStudentAttendances(Global.SELECTED_SUBJECT.getId(), attendance.getOnlyDate());

        studentAttendances.enqueue(new Callback<List<AttendanceDetail>>() {
            @Override
            public void onResponse(Call<List<AttendanceDetail>> call, Response<List<AttendanceDetail>> response) {
                List<AttendanceDetail> attendanceDetails = response.body();
                if (attendanceDetails.size() > 0) {
                    buildRecyclerView(attendanceDetails);
                }
            }

            @Override
            public void onFailure(Call<List<AttendanceDetail>> call, Throwable t) {

            }
        });
    }
}