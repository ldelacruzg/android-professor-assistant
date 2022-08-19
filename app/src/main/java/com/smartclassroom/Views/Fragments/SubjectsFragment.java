package com.smartclassroom.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.smartclassroom.R;
import com.smartclassroom.SubjectListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectsFragment extends Fragment {
    View view;
    ListView listViewSubjects;

    public SubjectsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_subjects, container, false);

        // init components
        initComponents();

        // build list view
        buildList();

        return view;
    }

    private void initComponents() {
        listViewSubjects = view.findViewById(R.id.listViewSubjects);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("Programación Orientada a Objeto");
        data.add("Aplicaciones Web");
        data.add("Aplicaciones Móviles");

        return data;
    }

    private void buildList() {
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
        SubjectListItemAdapter adapter = new SubjectListItemAdapter(
                getContext(),
                R.layout.subject_list_item,
                getData()
        );

        listViewSubjects.setAdapter(adapter);
    }
}