package com.smartclassroom.Views.Admin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.Models.User;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FingerprintFragment extends Fragment {
    View view;
    EditText editTextEmail, editTextEmailDelete, editTextUserIDDelete;
    Button buttonRegisterFingerprint, buttonDeleteFingerprint, buttonSearchDelete;
    LinearProgressIndicator progressIndicator;

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
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                String email = editTextEmail.getText().toString();
                if (Global.SMART_CLASSROOM_CONTROL_URL_BASE.contains("http")) {
                    if (!email.equals("")) {
                        Call<Void> registerFingerprint = RetrofitManager
                                .getSmartClassroomControl()
                                .registerFingerprint(email);

                        registerFingerprint.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(getContext(), "Start of registration", Toast.LENGTH_SHORT).show();
                                editTextEmail.setText("");
                                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "No server has been established", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSearchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                editTextUserIDDelete.setText("");
                String userEmail = editTextEmailDelete.getText().toString();
                if (!userEmail.equals("")) {
                    Call<User> userByEmail = RetrofitManager
                            .getSmartClassroomService()
                            .getUserByEmail(userEmail);

                    userByEmail.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                User currentUser = response.body();
                                if (currentUser.getId() >= 0) {
                                    editTextUserIDDelete.setText(currentUser.getId()+"");
                                }
                                else {
                                    Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            }
        });

        buttonDeleteFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                String userId = editTextUserIDDelete.getText().toString();
                if (Global.SMART_CLASSROOM_CONTROL_URL_BASE.contains("http")) {
                    if (!userId.equals("")) {
                        Call<Void> deleteFingerprint = RetrofitManager
                                .getSmartClassroomControl()
                                .deleteFingerprint(Integer.parseInt(userId));

                        deleteFingerprint.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(getContext(), "Fingerprint deleted", Toast.LENGTH_SHORT).show();
                                editTextEmailDelete.setText("");
                                editTextUserIDDelete.setText("");
                                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "No server has been established", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void initComponents() {
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonRegisterFingerprint = view.findViewById(R.id.buttonRegisterFingerprint);
        buttonDeleteFingerprint = view.findViewById(R.id.buttonDeleteFingerprint);

        editTextEmailDelete = view.findViewById(R.id.editTextEmailDelete);
        editTextUserIDDelete = view.findViewById(R.id.editTextUserIDDelete);
        buttonSearchDelete = view.findViewById(R.id.buttonSearchDelete);
        progressIndicator = view.findViewById(R.id.progressIndicator);
    }
}