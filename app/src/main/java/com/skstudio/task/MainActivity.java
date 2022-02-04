package com.skstudio.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText userEmail, password;
    private Button login_btn;

    //Admin PASSKEY
    private String Admin_Email = "Admin@kssmart.co";
    private String Admin_password = "123456";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.ET_UserName);
        password = findViewById(R.id.ET_Password);
        login_btn = findViewById(R.id.Btn_login);

        mAuth = FirebaseAuth.getInstance();



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String UserEmail = userEmail.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (UserEmail.isEmpty()){
                    userEmail.setError("Email is required!");
                    userEmail.requestFocus();
                    return;
                }
                if (Password.isEmpty()){
                    password.setError("Password is required!");
                    password.requestFocus();
                    return;
                }


                if (UserEmail.equals(Admin_Email)&& Password.equals(Admin_password)) {

                    Intent AdminScreen_intent = new Intent(MainActivity.this, AdminScreen.class);
                    startActivity(AdminScreen_intent);
                    finish();

                }else{
                    mAuth.signInWithEmailAndPassword(UserEmail,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent UserScreen_intent = new Intent(MainActivity.this,UserScreen.class);
                                        startActivity(UserScreen_intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}