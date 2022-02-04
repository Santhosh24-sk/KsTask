package com.skstudio.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProject extends AppCompatActivity {


    private TextView tv_projectname, tv_projectdate, tv_cname, tv_carchphrase, tv_website,tv_location;

    private FirebaseUser user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);

        tv_projectname = findViewById(R.id.TV_ProjectName);
        tv_projectdate = findViewById(R.id.TV_ProjectDate);
        tv_cname = findViewById(R.id.TV_CName);
        tv_carchphrase = findViewById(R.id.TV_CarchPhrase);
        tv_website = findViewById(R.id.TV_Website);
        tv_location = findViewById(R.id.TV_Location);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("project");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Project project = snapshot.getValue(Project.class);
                if (project != null){

                    String ProjectName = project.ProjectName;
                    String ProjectDate = project.ProjectDate;
                    String CName = project.Cname;
                    String CatchPhrase = project.CatchPhrase;
                    String Location = project.Location;
                    String Website = project.Website;

                    tv_projectname.setText(ProjectName);
                    tv_projectdate.setText(ProjectDate);
                    tv_cname.setText(CName);
                    tv_carchphrase.setText(CatchPhrase);
                    tv_location.setText(Location);
                    tv_website.setText(Website);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to project details", Toast.LENGTH_SHORT).show();
            }
        });


    }
}