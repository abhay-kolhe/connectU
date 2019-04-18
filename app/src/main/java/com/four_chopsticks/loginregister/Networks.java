package com.four_chopsticks.loginregister;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Measure;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Networks extends AppCompatActivity {
    EditText genre;
    EditText networktitle;
    EditText descriptions;
    ImageView coverPhoto;
    Button saveButton;
    UploadNetwork up =new UploadNetwork();

    String imageName= UUID.randomUUID().toString()+".jpg";
    String userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    String newKey =FirebaseDatabase.getInstance().getReference().push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networks);
        coverPhoto=(ImageView) findViewById(R.id.netwok_cover_image);
        saveButton=(Button) findViewById(R.id.network_save_button);
        genre=(EditText) findViewById(R.id.network_genre);
        networktitle=(EditText) findViewById(R.id.network_title);
        descriptions=(EditText) findViewById(R.id.network_desc);

        coverPhoto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //if no permissions request permission
                    requestPermissions(new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    getPhoto();
                }


            }
        });




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                up.setNetworkTitle(networktitle.getText().toString());
                up.setGenre(genre.getText().toString());
                up.setDescriptions(descriptions.getText().toString());
                //adding date CREAAED
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int inputmonth = c.get(Calendar.MONTH);
                String month = monthName(inputmonth);
                int year = c.get(Calendar.YEAR);
                final String currentDate = day + "." + month + "." + year;
                up.setCreatedTime(currentDate);


                try {
                    // Get the data from an ImageView as bytes
                    coverPhoto.setDrawingCacheEnabled(true);
                    coverPhoto.buildDrawingCache();
                    Bitmap bitmaps = ((BitmapDrawable) coverPhoto.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datas = baos.toByteArray();
                    //till here data has our image details which will be stored in database
                    final UploadTask uploadTask = FirebaseStorage.getInstance()
                            .getReference().child("Network coverImage")
                            .child(userId).child(newKey).child(imageName).putBytes(datas);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads

                            Toast.makeText(Networks.this, "Upload Failed! Please try again after some time",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            UploadTask.TaskSnapshot inputdownloadUrl = uploadTask.getResult();
                            up.setCoverPhotoUrl(inputdownloadUrl.toString());


                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplication(),"Please try again",Toast.LENGTH_SHORT)
                            .show();
                }
                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> userNetwork = new HashMap<String, String>() {{
                            if (up.getNetworkTitle().isEmpty()
                                    && up.getGenre().isEmpty()
                                    && up.getDescriptions().isEmpty()
                                    && up.getCreatedTime().isEmpty()
                                    ) {
                                Toast.makeText(Networks.this,
                                        "Please enter complete details",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                put("cover image link",up.getCoverPhotoUrl());
                                put("Title", up.getNetworkTitle());
                                put("Genre", up.getGenre());
                                put("Description", up.getDescriptions());
                                put("created at", up.getCreatedTime());

                            }
                        }};

                        //adding network details to database

                        DatabaseReference databaseReferences = FirebaseDatabase.getInstance()
                                .getReference();

                        databaseReferences.child("Networks").child(userId)
                                .child(newKey).setValue(userNetwork);
                        Toast.makeText(getApplication(), "Network created successfully",
                                Toast.LENGTH_SHORT).show();

                    }

                },10000);

                }

        });



    }
    //We already have permission if runs
    //after selecting images what should happen Written here.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //URI gets location of selected image
        try {
            Uri selectedImage = data.getData();

            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver()
                            , selectedImage);
                    //update imageView
                    coverPhoto.setImageBitmap(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
        e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getPhoto();
            }
        }
    }
    public void getPhoto(){
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI);
            //activity of gallery opens up after this onActivityForResult
            startActivityForResult(intent, 1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public String monthName(int inputMonth){
        switch(inputMonth){
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sept";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
                default:
                    return "bingo";
                }

    }


}
