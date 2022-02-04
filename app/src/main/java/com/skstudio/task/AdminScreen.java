package com.skstudio.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminScreen extends AppCompatActivity {

    private Button create_user, create_project, logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        create_user = findViewById(R.id.create_user);
        create_project = findViewById(R.id.create_project);
        logout = findViewById(R.id.logout);


        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_user_intent = new Intent(AdminScreen.this,CreateUser.class);
                startActivity(create_user_intent);

            }
        });
        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_project_intent = new Intent(AdminScreen.this,CreateProject.class);
                startActivity(create_project_intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout_intent = new Intent(AdminScreen.this,MainActivity.class);
                startActivity(logout_intent);
                finish();
            }
        });

    }
}