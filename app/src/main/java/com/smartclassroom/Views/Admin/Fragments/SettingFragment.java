package com.smartclassroom.Views.Admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;

public class SettingFragment extends Fragment {
    View view;
    EditText editTextDeviceIp;
    Button buttonSync;
    TextView textViewUrlDevice;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        initComponents();

        setTextViewUrlDevice();

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.SMART_CLASSROOM_CONTROL_URL_BASE = "http://" + editTextDeviceIp.getText().toString() + "/";
                setTextViewUrlDevice();
                Toast.makeText(getContext(), "The device's IP was set", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initComponents() {
        editTextDeviceIp = view.findViewById(R.id.editTextDeviceIp);
        buttonSync = view.findViewById(R.id.buttonSync);
        textViewUrlDevice = view.findViewById(R.id.textViewUrlDevice);
    }

    private void setTextViewUrlDevice() {
        textViewUrlDevice.setText(Global.SMART_CLASSROOM_CONTROL_URL_BASE);
    }
}