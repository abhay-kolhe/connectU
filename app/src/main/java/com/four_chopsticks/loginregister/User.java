package com.four_chopsticks.loginregister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.ImmutableSortedMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class User extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText dob;
    Button saveButton;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firstName=(EditText) findViewById(R.id.firstName);
        lastName=(EditText) findViewById(R.id.lastName);
        dob=(EditText) findViewById(R.id.dob);
        saveButton = (Button) findViewById(R.id.saveButton);


        //get reference to users childnode.
         databaseReference= FirebaseDatabase.getInstance()
                .getReference("Users");

        saveButton.setOnClickListener(new View.OnClickListener() {
            String uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            String email= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

            @Override
            public void onClick(View view) {
            final String inputFirstName=firstName.getText().toString();
            final String inputLastName=lastName.getText().toString();
            final String inputDob=dob.getText().toString();

            Map<String,String> profileMap=new HashMap<String, String>(){{
                if(inputFirstName.isEmpty()){
                    put("First Name","null");
                }else{
                    put("First Name",inputFirstName);
                }
                if(inputLastName.isEmpty()){
                    put("Last Name","null");
                }else{
                    put("Last Name",inputLastName);
                }
                if(inputDob.isEmpty()){
                    put("Birth Date","null");
                }else{
                    put("Birth Date",inputDob);
                }
                put("email",email);

            }};

            //send data to database
            databaseReference.child(uid).setValue(profileMap);
            startActivity(new Intent(User.this,MainActivity.class));
            finish();




            }
        });



    }
    private void createUser(String firstName, String lastName,String dob,String uid) {



        //save to database



    }




}
