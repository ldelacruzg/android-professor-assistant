package com.smartclassroom.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.smartclassroom.Adapters.SubjectExpandableListAdapter;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;
import com.smartclassroom.Views.ViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectsFragment extends Fragment {
    View view;

    ExpandableListView listViewSubjects;
    Map<String, List<String>> subjectListGroupChild;

    Teacher teacher;

    public SubjectsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_subjects, container, false);

        // init components
        initComponents();

        // Recuperamos al profesor
        //teacher = Global.GSON_INSTANCE.fromJson(getArguments().getString("teacher"), Teacher.class);

        // build list view
        buildExpandableList();

        return view;
    }

    private void initComponents() {
        listViewSubjects = view.findViewById(R.id.listViewSubjects);
    }

    private void bindData() {

    }

    private Map<String, List<String>> getList(List<Subject> subjectListGroup) {
        subjectListGroupChild = new HashMap<>();
        for (Subject subject : subjectListGroup) {
            subjectListGroupChild.put(subject.getName(), new ArrayList<String>() {
                {
                    add("Students");
                    add("Attendance");
                }
            });
        }

        return subjectListGroupChild;
    }

    private void buildExpandableList() {
        /*subjectListGroup = new ArrayList<>();
        subjectListGroup.add("Programación Orientada a Objectos");
        subjectListGroup.add("Aplicaciones Web");
        subjectListGroup.add("Aplicaciones Móviles");

        return subjectListGroup;*/

        /*return new ArrayList<Teacher>() {{
            add(new Teacher("Orlando", "Erazo", "oerazo@uteq.edu.ec"));
        }};*/

        /*return new ArrayList<Subject>() {{
            add(new Subject("Object-oriented programming"));
            add(new Subject("Web Applications"));
            add(new Subject("Mobile Applications"));
        }};*/

        Call<List<Subject>> subjectsByTeacher = RetrofitManager
                .getSmartClassroomService()
                .getSubjectsByTeacher("gleiston@gmail.com");
        
        subjectsByTeacher.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                List<Subject> subjectList = response.body();
                if (subjectList.size() > 0)
                    buildList(subjectList, getList(subjectList));
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {

            }
        });
    }

    private void buildList(List<Subject> subjectListGroup, Map<String, List<String>> subjectListGroupChild) {
        // Option 1 Simple Item
        // Adaptador, la forma en que mostraremos los datos (item)
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                getData()
        );

        // Se enlaza el adapter con el listView
        listViewSubjects.setAdapter(adapter);
        */

        // Option 2 Custom Item
        /*
        SubjectListItemAdapter adapter = new SubjectListItemAdapter(
                getContext(),
                R.layout.subject_list_group_child,
                getData()
        );

        listViewSubjects.setAdapter(adapter);
         */

        // Option 3 Expandable List View
        SubjectExpandableListAdapter adapter = new SubjectExpandableListAdapter(
                getContext(),
                subjectListGroup,
                subjectListGroupChild
        );

        listViewSubjects.setAdapter(adapter);
    }
}