package com.skstudio.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBio extends AppCompatActivity {

    private TextView name,password,dob;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bio);

        name = findViewById(R.id.tv_Name);
        password = findViewById(R.id.tv_Password);
        dob = findViewById(R.id.tv_DOB);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        Userid = user.getUid();

        reference.child(Userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String Fullname = userProfile.username;
                    String Email = userProfile.email;
                    String DOB = userProfile.dob;

                    name.setText(Fullname);
                    password.setText(Email);
                    dob.setText(DOB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load user details", Toast.LENGTH_SHORT).show();
            }
        });



    }
}