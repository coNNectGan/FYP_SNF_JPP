package com.example.snf_jpp.complainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snf_jpp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class complainerLogin extends AppCompatActivity {

    //vies
    EditText StaffidED, PasswordED;
    Button loginComplainer;
    TextView recoverLoginTextView;

    //remember box
    private CheckBox rmbMe;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    //progressdialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainer_login);

        //--
        mPrefs = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Complainer Login Page");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        //init
        StaffidED = findViewById(R.id.staffidED);
        PasswordED = findViewById(R.id.passwordED);
        recoverLoginTextView = findViewById(R.id.textViewForgotP);
        loginComplainer = findViewById(R.id.BTNLogin);
        rmbMe = findViewById(R.id.checkBoxRme);

//        remember me button click
        //
        rmbMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rmbMe.isChecked()){
                    Boolean boolIsChecked = rmbMe.isChecked();
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("pref_name",StaffidED.getText().toString());
                    editor.putString("pref_pass",PasswordED.getText().toString());
                    editor.putBoolean("pref_check",boolIsChecked);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Account Info Saved",Toast.LENGTH_SHORT).show();
                } else {
                    mPrefs.edit().clear().apply();
                }
            }
        });

        //login button click

        //preference method
        getPreferencesData();

        loginComplainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String staffID = StaffidED.getText().toString();
                String password = PasswordED.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(staffID).matches()) {
                    //invalid email pattern set error
                    StaffidED.setError("Invalid ID");
                    StaffidED.setFocusable(true);

                } else {
                    //valid email pattern
                    loginUser(staffID, password);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString(StaffidED.getText().toString(),staffID);
//                    editor.putString(PasswordED.getText().toString(),password);
//                    editor.commit();
//                    Toast.makeText(getApplicationContext(),"Successfully login",Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(complainerLogin.this,complainerDashBoard.class);
//                    startActivity(i);
                }
//                startActivity(new Intent(complainerLogin.this, DashBoardActivity.class));
            }
        });




        //not have account text
//        registerTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(complainerLogin.this, RegisterActivity.class));
//                finish();
//            }
//        });

        //recover pass textview click
        recoverLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

        //init progress dialog
        pd = new ProgressDialog(this);


    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")){
            String u = sp.getString("pref_name","not found.");
            StaffidED.setText(u.toString());
        }
        if(sp.contains("pref_pass")){
            String p = sp.getString("pref_pass","not found");
            PasswordED.setText(p.toString());
        }
        if(sp.contains("pref_check")){
            Boolean b = sp.getBoolean("pref_check",false);
            rmbMe.setChecked(b);
        }
    }

    private void showRecoverPasswordDialog() {
        //AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        //set layout linear layout
        LinearLayout linearLayout = new LinearLayout(this);
        //views to set in dialog
        final EditText staffidET = new EditText(this);
        staffidET.setHint("Email");
        staffidET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        //
        staffidET.setMinEms(16);

        linearLayout.addView(staffidET);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        //buttons recover
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String staffid = staffidET.getText().toString().trim();
                beginRecovery(staffid);

            }
        });

        //buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();

            }
        });

        //show dialog
        builder.create().show();


    }

    private void beginRecovery(String staffid) {
        //show progress dialog
        pd.setMessage("Sending email...");
        pd.show();

        mAuth.sendPasswordResetEmail(staffid)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(complainerLogin.this,"Email sent", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(complainerLogin.this,"Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                //get and show proper error message
                Toast.makeText(complainerLogin.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(final String staffID, final String password) {
        //show progress dialog
        pd.setMessage("Logging in...");
        pd.setCancelable(false);
        pd.show();
        mAuth.signInWithEmailAndPassword(staffID, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            //dismiss progress dialog
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //--------------------------------------------------------------------------------------------------
//                            //modify code
//                            String RegisteredUserID = user.getUid();
//                            //Logindatabase
//                            DatabaseReference jLoginDatabase;
//                            jLoginDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID);
//
//                            jLoginDatabase.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    String userType = dataSnapshot.child("userType").getValue().toString();
//
//                                    if(userType.equals("Technician")){
//                                        Intent intentTechnician = new Intent(LoginActivity.this,)
//                                    }
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//------------------------------------------------------------------------------------------------------------------------------------

                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
//Get user email and uid from auth
                                String StaffID = user.getEmail();
                                String uid = user.getUid();
                                //when user is registered store user info in firebase realtime database too

                                //using HashMap
                                HashMap<Object,String> hashMap = new HashMap<>();
                                //put info in hashmap
                                hashMap.put("StaffID",StaffID);
                                hashMap.put("uid",uid);
                                hashMap.put("position","");
                                hashMap.put("name",""); // add later
                                hashMap.put("email","");
                                hashMap.put("phone","");// add later
                                hashMap.put("image","");// add later
                                hashMap.put("cover","");// add later

                                //firebase database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //path to store user data named "users"
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);

                                  //to keep user logged
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString(StaffidED.getText().toString(),staffID);
//                                editor.putString(PasswordED.getText().toString(),password);
//                                editor.commit();
//                                Toast.makeText(getApplicationContext(),"Successfully login",Toast.LENGTH_SHORT).show();
//
//                                Intent i = new Intent(complainerLogin.this,complainerDashBoard.class);
//                                startActivity(i);
                            }

                            verifyUserType();

                        } else {
                            //dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.

                            Toast.makeText(complainerLogin.this, "Login fail. Please check your network connection or enter correct email and password.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //dismiss progress dialog
                pd.dismiss();
                //get error msg
                Toast.makeText(complainerLogin.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    //verify user type
    private void verifyUserType() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("position").getValue(String.class);
                if (userType != null && userType.equals("complainer")) {
                    Toast.makeText(complainerLogin.this, "Login success.",
                            Toast.LENGTH_SHORT).show();
                    //user is logged in, so start loginactivity
                    startActivity(new Intent(complainerLogin.this, complainerDashBoard.class));

                    StaffidED.getText().clear();
                    PasswordED.getText().clear();

                    finish();

                } else {

                    Toast.makeText(complainerLogin.this, "Login fail!Please make choose correct user types.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(complainerLogin.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go previous activity
        return super.onSupportNavigateUp();
    }
}
