package com.skstudio.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CreateProject extends AppCompatActivity {

    private EditText et_projectname, et_projectdate;
    private TextView tv_cname, tv_carchphrase, tv_website,tv_location;
    private Button create;

    private FirebaseAuth mAuth;
    private DatabaseReference reference,referenceName, referenceproject;
    private Spinner spinner;

    private String Date,projectname,projectdate,location,website,cname,catchphrase,assignUser;
    private String userid ;
    List<String> userList;
    List<String> stringList = new ArrayList<>();
    List<String> NameList = new ArrayList<>();

    private String JSON_Url = "https://jsonplaceholder.typicode.com/users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        et_projectname = findViewById(R.id.ET_ProjectName);
        et_projectdate = findViewById(R.id.ET_ProjectDate);
        tv_cname = findViewById(R.id.TV_CName);
        tv_carchphrase = findViewById(R.id.TV_CarchPhrase);
        tv_website = findViewById(R.id.TV_Website);
        tv_location = findViewById(R.id.TV_Location);
        create = findViewById(R.id.create_project_btn);
        spinner = findViewById(R.id.spinner);

        userList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        requestjson();
        fetchUsers();



        et_projectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateProject.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month+1;
                                Date = day+"/"+month+"/"+year;
                                et_projectdate.setText(Date);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                projectname = et_projectname.getText().toString();
                projectdate = et_projectdate.getText().toString();
                cname = tv_cname.getText().toString();
                catchphrase = tv_carchphrase.getText().toString();
                location = tv_location.getText().toString();
                website = tv_website.getText().toString();
                assignUser = spinner.getSelectedItem().toString();



                for(int j = 0; j< NameList.size();j++){
                    if (assignUser == NameList.get(j)){
                        userid = stringList.get(j);
                    }
                }

                Project project = new Project(projectname,projectdate,cname,catchphrase,location,website);

                referenceproject = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                referenceproject.child("project").setValue(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Project Uploaded", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Project Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });



    }

    private void fetchUsers() {



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    userList.clear();
                    stringList.clear();
                    NameList.clear();

                    for (DataSnapshot dss: snapshot.getChildren()) {

                        stringList.add(dss.getKey());

                    }
                    //Toast.makeText(getApplicationContext(),stringList.toString(), Toast.LENGTH_LONG).show();

                    for (int i = 0; i<stringList.size();i++){

                        referenceName =  reference.child(stringList.get(i));

                        referenceName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    User username_list = snapshot.getValue(User.class);
                                    if (username_list!=null){
                                        String username = username_list.username;
                                        NameList.add(username);
                                        ArrayAdapter adapter = new ArrayAdapter(CreateProject.this,
                                                android.R.layout.simple_spinner_item,NameList);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner.setAdapter(adapter);
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        //Toast.makeText(getApplicationContext(),stringList.get(i), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               // Toast.makeText(getApplicationContext(), snapshot.toString(), Toast.LENGTH_SHORT).show();
                if (snapshot.exists()){
                    userList.clear();
                    for (DataSnapshot dss: snapshot.getChildren()){

                        userList.add(dss.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i =0 ; i<userList.size();i++){
                        stringBuilder.append(userList.get(i) + ",");
                    }

                    Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    private void requestjson() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(1);
                    tv_cname.setText(jsonObject.getJSONObject("company").getString("name"));
                    tv_carchphrase.setText(jsonObject.getJSONObject("company").getString("catchPhrase"));
                    tv_location.setText("lat : " + jsonObject.getJSONObject("address").getJSONObject("geo").getString("lat") +
                            " , lng :" + jsonObject.getJSONObject("address").getJSONObject("geo").getString("lng"));
                    tv_website.setText(jsonObject.getString("website"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonArrayRequest);
    }
}