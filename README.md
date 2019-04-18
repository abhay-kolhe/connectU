# connectU
firebase
                                                     //APP IS IN BUILDING STATE//
apply plugin: 'com.android.application'
APP/GRADLE
android {
    compileSdkVersion 27
    defaultConfig {
        applicationId "com.four_chopsticks.loginregister"
        minSdkVersion 22
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    //noinspection GradleCompatible
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation 'com.android.support:design:27.1.1'
    implementation 'com.android.support:cardview-v7:27.1.1'
    implementation 'com.github.bumptech.glide:glide:4.6.1'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'com.google.firebase:firebase-core:16.0.8'
    implementation 'com.google.firebase:firebase-auth:16.2.1'
    implementation 'com.google.firebase:firebase-database:16.1.0'
    implementation 'com.google.firebase:firebase-storage:16.1.0'
    implementation 'com.android.support:support-v4:27.1.1'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}

apply plugin: 'com.google.gms.google-services'

###PLEASE SELECT ACCORDING TO UPDATES AVAILABLE.

2. MANIFEST 

Add 
 <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    
    in manifest.
    
    
    
    There are many sub-parts to this application.
   
    ~Authentication by FirebaseAuth
    ~Network creation
    ~joining network
    
    
                                    AUTHENTICATION
                                    
   1.Signup Activity
   
   
   package com.four_chopsticks.loginregister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private Button btnSignIn , btnSignUp , btnResetPassword;
    private ProgressBar progressBar;
    private DatabaseReference mRef;//Write and Read
    private FirebaseAuth auth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //get Firebase auth instance

        auth= FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
        mRef=database.getInstance().getReference();

        btnSignIn=(Button) findViewById(R.id.sign_in_button);
        btnSignUp=(Button) findViewById(R.id.sign_up_button);
        inputEmail=(EditText) findViewById(R.id.email);
        inputPassword=(EditText) findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword=(Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email address!"
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter password"
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password too short," +
                                    "enter minimum 6 characters!"
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                //CREATE USER
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this
                                , new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(SignUpActivity.this,"Account created!" , Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.

                                        if(!task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();

                                        }else{
                                            //Sending email to user database

                                            mRef.child("Users").child(task.getResult().getUser().getUid());
                                            //database sent over here.

                                            startActivity(new Intent(SignUpActivity.this,User.class));
                                            finish();

                                        }
                                    }
                                });


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

layout_signup


<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".SignUpActivity">

    <LinearLayout
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:background="@color/colorPrimaryDark"
        android:gravity="center"
        android:orientation="vertical"
        android:padding="@dimen/activity_horizontal_margin">

        <ImageView
            android:layout_width="@dimen/logo_w_h"
            android:layout_height="@dimen/logo_w_h"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="30dp"
            android:contentDescription="@string/app_name"
            android:src="@drawable/ic_action_name" />

        <EditText
            android:id="@+id/email"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/email"
            android:inputType="textEmailAddress"
            android:maxLines="1"
            android:singleLine="true"
            android:textColor="@android:color/white" />
        <EditText
            android:id="@+id/password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:focusableInTouchMode="true"
            android:hint="@string/hint_password"
            android:imeActionId="@+id/login"
            android:imeOptions="actionUnspecified"
            android:inputType="textPassword"
            android:maxLines="1"
            android:singleLine="true"
            android:textColor="@android:color/white"
            tools:ignore="InvalidImeActionId" />
        <Button
            android:id="@+id/sign_up_button"
            style="?android:textAppearanceSmall"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:background="@color/colorAccent"
            android:text="@string/action_sign_in_short"
            android:textColor="@android:color/black"
            android:textStyle="bold" />

        <Button
            android:id="@+id/btn_reset_password"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dip"
            android:background="@null"
            android:text="@string/btn_forgot_password"
            android:textAllCaps="false"
            android:textColor="@color/colorAccent" />

        <Button
            android:id="@+id/sign_in_button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:background="@null"
            android:text="@string/btn_link_to_login"
            android:textAllCaps="false"
            android:textColor="@color/white"
            android:textSize="15dp" />

    </LinearLayout>
    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="30dp"
        android:layout_height="30dp"
        android:layout_gravity="center|bottom"
        android:layout_marginBottom="20dp"
        android:visibility="gone" />

</android.support.constraint.ConstraintLayout>



3.SignIn Activity

