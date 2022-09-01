package com.smartclassroom.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.R;

public class DevicesFragment extends Fragment {
    View view;
    SwitchMaterial switchLight, switchProjector, switchAir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_devices, container, false);

        // init components
        initComponents();

        switchLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click = " + switchLight.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initComponents() {
        switchLight = view.findViewById(R.id.switchLight);
        switchProjector = view.findViewById(R.id.switchProjector);
        switchAir = view.findViewById(R.id.switchAir);
    }
}