package com.skstudio.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateUser extends AppCompatActivity {

    private EditText userName, userEmail, password, date_of_birth;
    private Button createUser;
    DatePickerDialog.OnDateSetListener setListener;

    private FirebaseAuth mAuth;

    private String UserName, UserEmail,Password, Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        userName = findViewById(R.id.ET_UserName);
        userEmail = findViewById(R.id.ET_Email);
        password = findViewById(R.id.ET_Password);
        date_of_birth = findViewById(R.id.ET_DOB);
        createUser = findViewById(R.id.Btn_create);

        mAuth = FirebaseAuth.getInstance();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateUser.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month+1;
                                Date = day+"/"+month+"/"+year;
                                date_of_birth.setText(Date);
                            }
                        },year,month,day);
                        datePickerDialog.show();
            }
        });

       /* setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                date_of_birth.setText(date);
            }
        };*/


        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserEmail = userEmail.getText().toString();
                Password = password.getText().toString();
                UserName = userName.getText().toString();

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
                if (UserName.isEmpty()){
                    userName.setError("Username is required");
                    userName.requestFocus();
                    return;
                }
               /* if (Patterns.GOOD_IRI_CHAR.matches(UserEmail)){
                    createUser();

                }*/
                else{

                    createUser();
                }
            }
        });
    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(UserEmail,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(UserName,UserEmail,Date);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User created successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Failed to create users",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed to create user",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}