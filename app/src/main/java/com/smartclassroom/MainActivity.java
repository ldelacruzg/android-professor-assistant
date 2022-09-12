package com.smartclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.smartclassroom.Models.Classroom;
import com.smartclassroom.Models.Student;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.Models.User;
import com.smartclassroom.Services.SmartClassroomService;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;
import com.smartclassroom.Views.Admin.AdminActivity;
import com.smartclassroom.Views.ViewActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String SMART_CLASSROOM_API_URL_BASE = "https://blooming-tundra-94814.herokuapp.com/api/";
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    LinearProgressIndicator progressIndicator;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                if (validateCredencials()) {
                    signIn();
                } else {
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initComponents() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressIndicator = findViewById(R.id.progressIndicator);

        editTextEmail.setText("gleiston@gmail.com");
        editTextPassword.setText("123456789");
    }

    private boolean validateCredencials() {
        return (editTextEmail.getText() != null && editTextPassword.getText() != null);
    }

    private void signIn() {
        // Se realizá la petición
        Call<Teacher> teacherByEmail = RetrofitManager.getSmartClassroomService()
                .getTeacherByEmail(editTextEmail.getText().toString());

        teacherByEmail.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                Teacher teacher = response.body();
                if (teacher != null) {
                    // user exist
                    if (teacher.getPassword().equals(editTextPassword.getText().toString())) {
                        if (teacher.getUserType().getName().equals("profesor")) {
                            // Acceso al ViewActivity
                            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                            intent.putExtra("teacher", gson.toJson(teacher));
                            Global.LOGGED_TEACHER = teacher;
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                            startActivity(intent);
                            finish();
                            return;
                        } else if (teacher.getUserType().getName().equals("administrador")) {
                            // Acceso al AdminActivity
                            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                            Global.LOGGED_TEACHER = teacher;
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                }

                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                // uset not exist
                Toast.makeText(MainActivity.this, "Invalid email or password...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }
}