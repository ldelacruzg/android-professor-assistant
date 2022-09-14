package com.smartclassroom.Views.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.Adapters.DeviceListItemAdapter;
import com.smartclassroom.Adapters.StudentListItemAdapter;
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
    Button buttonIpSync;
    EditText editTextDeviceIp;

    // NEW
    RecyclerView recyclerViewDevices;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_devices, container, false);

        // init components
        initComponents();

        // build recycler view
        buildRecyclerView();

        buttonIpSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.SMART_CLASSROOM_CONTROL_URL_BASE = "http://" + editTextDeviceIp.getText().toString() + "/";
                Toast.makeText(getContext(), "The device's IP was set", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initComponents() {
        buttonIpSync = view.findViewById(R.id.buttonIpSync);
        editTextDeviceIp = view.findViewById(R.id.editTextDeviceIp);

        recyclerViewDevices = view.findViewById(R.id.recyclerViewDevices);
    }

    private void buildRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new DeviceListItemAdapter(R.layout.device_list_item, Global.LOGGED_TEACHER.getDevices());
        recyclerViewDevices.setAdapter(adapter);
        recyclerViewDevices.setLayoutManager(layoutManager);
    }
}
