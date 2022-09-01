package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.smartclassroom.Adapters.StudentListItemAdapter;
import com.smartclassroom.Models.Student;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.R;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsActivity extends AppCompatActivity {
    TextView textViewSubjectName;
    RecyclerView recyclerViewStudents;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    LinearProgressIndicator progressIndicatorStudents;

    Gson gson = new Gson();
    Subject subject;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initial components
        initComponents();

        // Subject Load from a Intent
        bundle = getIntent().getExtras();
        subject = gson.fromJson(bundle.getString("subject"), Subject.class);

        // Placing data in the components
        bindData();
    }

    private void initComponents() {
        // Components
        textViewSubjectName = findViewById(R.id.textViewSubjectName);
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        progressIndicatorStudents = findViewById(R.id.progressIndicator);
    }

    private void bindData() {
        // Set text Subject Name
        textViewSubjectName.setText(subject.getName());

        // Build recycler view
        requestStudents();
    }

    private void buildRecyclerView(List<Student> studentList) {
        layoutManager = new LinearLayoutManager(this);
        adapter = new StudentListItemAdapter(studentList, R.layout.student_list_item);
        recyclerViewStudents.setAdapter(adapter);
        recyclerViewStudents.setLayoutManager(layoutManager);
    }

    private void requestStudents() {
        progressIndicatorStudents.setVisibility(LinearProgressIndicator.VISIBLE);
        Call<Subject> subjectById = RetrofitManager.getSmartClassroomService().getSubjectById(subject.getId());
        subjectById.enqueue(new Callback<Subject>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                List<Student> studentList = response.body().getStudents();
                List<Student> list = new ArrayList<>();
                for (Student student : studentList) {
                    if (!student.getUserType().getName().equals("profesor")
                            && !student.getUserType().getName().equals("administrador")) {
                        list.add(student);
                    }
                }
                studentList = list;
                studentList.sort((e1, e2) -> e1.getLastname().compareTo(e2.getLastname()));
                buildRecyclerView(studentList);
                progressIndicatorStudents.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {

            }
        });
    }
}