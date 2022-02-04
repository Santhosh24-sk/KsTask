package com.skstudio.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserScreen extends AppCompatActivity {

    private Button mybio,myproject,logout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        mybio = findViewById(R.id.MyBio);
        myproject = findViewById(R.id.MyProject);
        logout = findViewById(R.id.Logout);

        mAuth = FirebaseAuth.getInstance();

        mybio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mybio = new Intent(UserScreen.this,MyBio.class);
                startActivity(mybio);
            }
        });

        myproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myproject = new Intent(UserScreen.this,MyProject.class);
                startActivity(myproject);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent logout_intent = new Intent(UserScreen.this,MainActivity.class);
                startActivity(logout_intent);
                finish();
            }
        });

    }
}