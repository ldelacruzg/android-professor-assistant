package com.smartclassroom.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;

public class DevicesFragment extends Fragment {
    View view;
    SwitchMaterial switchLight, switchProjector, switchAir;
    Button buttonIpSync;
    EditText editTextDeviceIp;

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

        buttonIpSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.SMART_CLASSROOM_CONTROL_URL_BASE = "http://" + editTextDeviceIp.getText().toString() + "/";
                System.out.println("base url production =====> " + Global.SMART_CLASSROOM_CONTROL_URL_BASE);
                Toast.makeText(getContext(), "The device's IP was set", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initComponents() {
        switchLight = view.findViewById(R.id.switchLight);
        switchProjector = view.findViewById(R.id.switchProjector);
        switchAir = view.findViewById(R.id.switchAir);
        buttonIpSync = view.findViewById(R.id.buttonIpSync);
        editTextDeviceIp = view.findViewById(R.id.editTextDeviceIp);
    }
}