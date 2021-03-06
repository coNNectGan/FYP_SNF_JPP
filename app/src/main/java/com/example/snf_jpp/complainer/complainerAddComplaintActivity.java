package com.example.snf_jpp.complainer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.snf_jpp.MainActivity;
import com.example.snf_jpp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class complainerAddComplaintActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;
    ActionBar actionBar;

    //permissions constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    //permissions array
    String[] cameraPermissions;
    String[] storagePermissions;

    //views
    EditText titleEt, descriptionEt;
    ImageView imageIv;
    Button uploadBtn;

    //new info to add
    Spinner worktype;
    Spinner status;
    EditText locationComplainer;

    //user info
    String name, email, uid, dp;

    //image picked will be samed in this uri
    Uri image_rui = null;

    //progress bar
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);


        actionBar = getSupportActionBar();
        getSupportActionBar().hide();
//        actionBar.setTitle("Add New Complaint");

        //enable back button in actionbar
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //init permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        pd = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        actionBar.setSubtitle(email);

        //get some info of current user to include in post
        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    name = ""+ ds.child("name").getValue();
                    email = ""+ds.child("email").getValue();
                    dp = ""+ ds.child("image").getValue();
//                    status =""+ds.child("status").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //init views
        titleEt = findViewById(R.id.pTitleEt);
        descriptionEt = findViewById(R.id.pDescriptionEt);
        imageIv = findViewById(R.id.work_Image);
        uploadBtn = findViewById(R.id.pUploadBtn);

        //new info to add
        worktype = findViewById(R.id.workTypeSpinner);
        locationComplainer = findViewById(R.id.locationComplainer);

        status = findViewById(R.id.StatusSpinner);
        status.setEnabled(false);

        //get image from camera/gellery on click
        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        //upload button click listener
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data(title,descrifption) from EditTexts
                String title = titleEt.getText().toString().trim();
                String description = descriptionEt.getText().toString().trim();
                String workType = worktype.getSelectedItem().toString();
                String statuS = status.getSelectedItem().toString();
                String location = locationComplainer.getText().toString().trim();

                //new

                if (TextUtils.isEmpty(title)){
                    Toast.makeText(complainerAddComplaintActivity.this,"Please enter the title name for complaint.", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(description)){
                    Toast.makeText(complainerAddComplaintActivity.this,"Please enter the descrption for complaint.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (image_rui ==null) {
                    //post without image
                    uploadData(title, description,"noImage",location,workType,statuS);


                }
                else {
                    //post with image
                    uploadData(title, description,String.valueOf(image_rui),location,workType,statuS);


                }
            }

        });

    }

    private void uploadData(final String title, final String description, String uri, final String location, final String worktype,final String status) {
        pd.setMessage("Creating the complaint...");
        pd.show();
        pd.setCancelable(false);

        //for post-image name, post-id, post-publish-time
        final String timeStamp = String.valueOf(System.currentTimeMillis());

        String filePathAndName = "Post/" + "post_" +timeStamp;

        if(!uri.equals("noImage")){
            //post with image
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //image is uploaded to firebase storage, how get it's uri
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();



                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();


                            if (uriTask.isSuccessful()) {
                                //url is received upload post to firebase database

                                //convert timestamp to dd/mm/yyyy hh:mm am/pm
                                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                                calendar.setTimeInMillis(Long.parseLong(timeStamp));
                                String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

                                HashMap<Object,String> hashMap = new HashMap<>();
                                //put post info
                                hashMap.put("uid",uid);
                                hashMap.put("uName",name);
                                hashMap.put("uEmail",email);
                                hashMap.put("uDp",dp);
                                hashMap.put("pId",timeStamp);
                                hashMap.put("pTitle",title);
                                hashMap.put("pDescription",description);

                                hashMap.put("location",location);
                                hashMap.put("worktype",worktype);

                                hashMap.put("pImage",downloadUri);
                                hashMap.put("pTime",pTime);

                                hashMap.put("status",status);

                                //path to store post data
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tasks");
                                //put data in this refer
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //added in database
                                                pd.dismiss();
                                                Toast.makeText(complainerAddComplaintActivity.this,"Complaint created.",Toast.LENGTH_SHORT).show();
                                                //reset views
                                                titleEt.setText("");
                                                descriptionEt.setText("");
                                                locationComplainer.setText("");

                                                imageIv.setImageURI(null);
                                                image_rui = null;

                                                Intent i = new Intent(complainerAddComplaintActivity.this, complainerDashBoard.class);
                                                startActivity(i);




                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //failed adding post in database
                                                pd.dismiss();
                                                Toast.makeText(complainerAddComplaintActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(complainerAddComplaintActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else {
            //post without image

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(Long.parseLong(timeStamp));
            String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

            HashMap<Object,String> hashMap = new HashMap<>();
            //put post info
            hashMap.put("uid",uid);
            hashMap.put("uName",name);
            hashMap.put("uEmail",email);
            hashMap.put("uDp",dp);
            hashMap.put("pId",timeStamp);
            hashMap.put("pTitle",title);
            hashMap.put("pDescription",description);
            hashMap.put("pImage","noImage");
            hashMap.put("pTime",pTime);

            hashMap.put("location",location);
            hashMap.put("worktype",worktype);

            hashMap.put("status",status);



            //path to store post data
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tasks");
            //put data in this refer
            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //added in database
                            pd.dismiss();
                            Toast.makeText(complainerAddComplaintActivity.this,"Complaint created",Toast.LENGTH_SHORT).show();
                            titleEt.setText("");
                            descriptionEt.setText("");
                            imageIv.setImageURI(null);
                            image_rui = null;

                            Intent i = new Intent(complainerAddComplaintActivity.this,complainerDashBoard.class);
                            startActivity(i);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed adding post in database
                            pd.dismiss();
                            Toast.makeText(complainerAddComplaintActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

        }


    }

    private void showImagePickDialog() {
        //options(camera, gallery) to show in dialog
        String[] options = {"Camera", "Gallery"};

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");
        //set options to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //item click handle
                if (which==0){
                    //camera clicked
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }

                }
                if (which==1){
                    //gallery clicked
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }

                }

            }
        });

        //create and show dialog
        builder.create().show();
    }

    private void pickFromGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        //set to imageview
        Toast.makeText(this,"Please pick a photo from your gallery.",Toast.LENGTH_SHORT).show();
        imageIv.setImageURI(image_rui);

        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        //intent to pick image from camera
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        Toast.makeText(this,"Please snap a photo to your phone.",Toast.LENGTH_SHORT).show();
        image_rui = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        //check if storage permission is enabled or not
        //return true if enabled
        //return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission(){
        //request runtime storage permission
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE);


    }

    private boolean checkCameraPermission(){
        //check if camera permission is enabled or not
        //return true if enabled
        //return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private void requestCameraPermission(){
        //request runtime camera permission
        ActivityCompat.requestPermissions(this,cameraPermissions, CAMERA_REQUEST_CODE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            //set email of logged in user
//            ProfileTv.setText(user.getEmail());
            email = user.getEmail();
            uid = user.getUid();
        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
    //back code
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go to previous activity
        return super.onSupportNavigateUp();
    }
    // menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

//        menu.findItem(R.id.action_add_complaint).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    //item selection code
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();

        }

        return super.onOptionsItemSelected(item);
    }

    //handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//this method is called when user press Allow or Deny from permission request dialog
        //here we will handle permission cases (allowed and denied)

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        //both permission are granted
                        pickFromCamera();
                    }
                    else {
                        //camera or gallery or both permissions were denied
                        Toast.makeText(this,"Camera & Storage both permissions are neccessary..." , Toast.LENGTH_SHORT).show();

                    }


                }else {

                }
            }
            break;

            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        //storage permission granted
                        pickFromGallery();
                    }
                    else {
                        //camera or gallery or both permissions were denied
                        Toast.makeText(this,"Camera & Storage both permissions are neccessary..." , Toast.LENGTH_SHORT).show();

                    }
                }

            }

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //this method will be called after picking image from camera or gallery

//        if (requestCode == RESULT_OK){
//            Toast.makeText(this,"SetImageView2",Toast.LENGTH_SHORT).show();
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //image is picked from gallery, get uri of image
                image_rui = data.getData();

                //set to imageview
                Toast.makeText(this,"Photo Imported.",Toast.LENGTH_SHORT).show();
                imageIv.setImageURI(image_rui);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //image is picked from camera, get uri of image
                Toast.makeText(this,"Photo Captured.",Toast.LENGTH_SHORT).show();
                imageIv.setImageURI(image_rui);

            }
//        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
