package com.smartclassroom.Views.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.Models.Device;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesFragment extends Fragment {
    View view;
    SwitchMaterial switchLight, switchProjector, switchAir;
    Button buttonIpSync;
    EditText editTextDeviceIp;
    LinearProgressIndicator progressIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_devices, container, false);

        // init components
        initComponents();

        // load config of devices
        loadDeviceStatus();

        switchLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<Void> changeDeviceStatus = RetrofitManager
                        .getSmartClassroomService()
                        .changeDeviceStatus(1, switchLight.isChecked());

                changeDeviceStatus.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "Device status changed", Toast.LENGTH_SHORT).show();
                            Global.LOGGED_TEACHER.getDevices().get(0).setEnable(switchLight.isChecked());
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        switchAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<Void> changeDeviceStatus = RetrofitManager
                        .getSmartClassroomService()
                        .changeDeviceStatus(3, switchAir.isChecked());

                changeDeviceStatus.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "Device status changed", Toast.LENGTH_SHORT).show();
                            Global.LOGGED_TEACHER.getDevices().get(1).setEnable(switchAir.isChecked());
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        switchProjector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<Void> changeDeviceStatus = RetrofitManager
                        .getSmartClassroomService()
                        .changeDeviceStatus(4, switchProjector.isChecked());

                changeDeviceStatus.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "Device status changed", Toast.LENGTH_SHORT).show();
                            Global.LOGGED_TEACHER.getDevices().get(2).setEnable(switchProjector.isChecked());
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        buttonIpSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Global.SMART_CLASSROOM_CONTROL_URL_BASE = "http://" + editTextDeviceIp.getText().toString() + "/";
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
        progressIndicator = view.findViewById(R.id.progressIndicator);
    }

    @SuppressLint("NewApi")
    private void loadDeviceStatus() {
        Global.LOGGED_TEACHER.getDevices().forEach(el -> {
            switch (el.getId()) {
                case 1:
                    switchLight.setChecked(el.isEnable());
                    break;
                case 3:
                    switchAir.setChecked(el.isEnable());
                    break;
                case 4:
                    switchProjector.setChecked(el.isEnable());
                    break;
            }
        });
    }
}
