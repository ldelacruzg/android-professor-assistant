package com.smartclassroom.Views.Admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartclassroom.R;
import com.smartclassroom.Utils.RetrofitManager;

import retrofit2.Call;

public class FingerprintFragment extends Fragment {
    View view;
    EditText editTextEmail;
    Button buttonRegisterFingerprint, buttonDeleteFingerprint;

    public FingerprintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fingerprint, container, false);

        initComponents();

        buttonRegisterFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Register fingerprint", Toast.LENGTH_SHORT).show();
                Call<Void> registerFingerprint = RetrofitManager
                        .getSmartClassroomControl()
                        .registerFingerprint(editTextEmail.getText().toString());
            }
        });

        buttonDeleteFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Delete fingerprint", Toast.LENGTH_SHORT).show();
                Call<Void> deleteFingerprint = RetrofitManager
                        .getSmartClassroomControl()
                        .deleteFingerprint(editTextEmail.getText().toString());
            }
        });

        return view;
    }

    private void initComponents() {
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonRegisterFingerprint = view.findViewById(R.id.buttonRegisterFingerprint);
        buttonDeleteFingerprint = view.findViewById(R.id.buttonDeleteFingerprint);
    }
}