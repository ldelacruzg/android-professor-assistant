package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartclassroom.Adapters.StudentListItemAdapter;
import com.smartclassroom.Models.Student;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.R;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    TextView textViewSubjectName;
    RecyclerView recyclerViewStudents;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Gson gson = new Gson();
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initial components
        initComponents();

        // Placing data in the components
        bindData();
    }

    private void initComponents() {
        // Components
        textViewSubjectName = findViewById(R.id.textViewSubjectName);
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
    }

    private void bindData() {
        // Set text Subject Name
        textViewSubjectName.setText(subject.getName());

        // Subject Load from a Intent
        Bundle bundle = getIntent().getExtras();
        subject = gson.fromJson(bundle.getString("subject"), Subject.class);

        // Build recycler view
        buildRecyclerView();
    }

    private List<Student> getAllStudents() {
        return new ArrayList<Student>() {{
            add(new Student("Luis", "De La Cruz", "ldelacruzg@uteq.edu.ec"));
            add(new Student("Lino", "Alcivar", "lalcivard@uteq.edu.ec"));
            add(new Student("Roberto", "Suarez", "rsuarez@uteq.edu.ec"));
        }};
    }

    private void buildRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new StudentListItemAdapter(getAllStudents(), android.R.layout.simple_list_item_activated_2);
        recyclerViewStudents.setAdapter(adapter);
        recyclerViewStudents.setLayoutManager(layoutManager);
    }
}