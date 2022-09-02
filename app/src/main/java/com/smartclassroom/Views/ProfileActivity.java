package com.smartclassroom.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartclassroom.MainActivity;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewFirstName, textViewLastName, textViewEmail, textViewPhone, textViewUserType, textViewFullName;
    Button buttonDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // init components
        initComponents();

        // set data
        bindData();

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewFirstName = findViewById(R.id.textViewFirstName);
        textViewLastName = findViewById(R.id.textViewLastName);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewUserType = findViewById(R.id.textViewUserType);
        textViewFullName = findViewById(R.id.textViewFullName);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);
    }

    private void bindData() {
        textViewFullName.setText(Global.LOGGED_TEACHER.getName() + " " + Global.LOGGED_TEACHER.getLastname());
        textViewPhone.setText(Global.LOGGED_TEACHER.getPhone());
        textViewUserType.setText(Global.LOGGED_TEACHER.getUserType().getName().toUpperCase());
        textViewLastName.setText(Global.LOGGED_TEACHER.getLastname());
        textViewFirstName.setText(Global.LOGGED_TEACHER.getName());
        textViewEmail.setText(Global.LOGGED_TEACHER.getEmail());
    }
}