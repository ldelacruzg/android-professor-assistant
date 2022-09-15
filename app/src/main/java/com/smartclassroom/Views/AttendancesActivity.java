package com.smartclassroom.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.smartclassroom.Adapters.AttendancesListItemAdapater;
import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendancesActivity extends AppCompatActivity {
    TextView textViewSubjectName;

    RecyclerView recyclerViewAttendances;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    LinearProgressIndicator progressIndicator;
    Button buttonShowDatePicker, buttonRefreshAttendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // init components
        initComponents();

        bindData();

        // Material Date picker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        builder.setTitleText("Select a date");

        MaterialDatePicker materialDatePicker = builder.build();
        buttonShowDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATA_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Object selection) {
                //LocalDate date = LocalDate.parse(materialDatePicker.getHeaderText());
                //LocalDate date = LocalDate.parse(selection.toString());
                //System.out.println("date selection===> " + date)
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis((Long) selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(calendar.getTime());
                System.out.println(date);

                requestAttendanceListByDate(date);

                Toast.makeText(AttendancesActivity.this, "Date seleted:" + date, Toast.LENGTH_SHORT).show();
            }
        });

        buttonRefreshAttendanceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAttendanceList();
            }
        });
    }

    private void initComponents() {
        textViewSubjectName = findViewById(R.id.textViewSubjectNameInAttendance);
        recyclerViewAttendances = findViewById(R.id.recyclerViewAttendances);
        progressIndicator = findViewById(R.id.progressIndicator);
        buttonShowDatePicker = findViewById(R.id.buttonShowDatePicker);
        buttonRefreshAttendanceList = findViewById(R.id.buttonRefreshAttendanceList);
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

    private void requestAttendanceListByDate(String date) {
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
                    List<Attendance> attendances = attendanceList.stream().filter(el -> el.getOnlyDate().equals(date)).collect(Collectors.toList());
                    buildRecyclerView(attendances);
                }
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {

            }
        });
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