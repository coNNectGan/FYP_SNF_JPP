package com.example.snf_jpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //vies
    EditText StaffidED, PasswordED;
    Button loginBTN, loginOfficer, loginAdmin;
    TextView registerTextView,recoverLoginTextView;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    //progressdialog
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_login);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login Page");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        //init
        StaffidED = findViewById(R.id.staffidED);
        PasswordED = findViewById(R.id.passwordED);
        registerTextView = findViewById(R.id.textViewRegister);
        recoverLoginTextView = findViewById(R.id.textViewForgotP);
        loginBTN = findViewById(R.id.BTNLogin);
        loginOfficer = findViewById(R.id.BTNLoginOfficer);


        //login button click
        loginBTN.setOnClickListener(new View.OnClickListener() {
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
                }
//                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
    }
});




        //not have account text
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

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
                            Toast.makeText(LoginActivity.this,"Email sent", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        //get and show proper error message
                            Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String staffID, String password) {
        //show progress dialog
        pd.setMessage("Logging in...");
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
                            }

                            //user is logged in, so start loginactivity
//                            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                            finish();

                        } else {
                            //dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //dismiss progress dialog
                pd.dismiss();
                //get error msg
                Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go previous activity
        return super.onSupportNavigateUp();
    }
}
