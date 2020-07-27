package com.example.snf_jpp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.snf_jpp.complainer.complainerLogin;
import com.example.snf_jpp.officer.officerLogin;
import com.example.snf_jpp.technician.technicianLogin;

public class MainActivity extends AppCompatActivity {

    //views
    Button mLoginTechnician,mLoginOfficer,mLoginComplainer;
    TextView needhelpTV;
//    TextView registerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //initial views
        mLoginTechnician = findViewById(R.id.BTNLogin);
        mLoginOfficer = findViewById(R.id.BTNLoginOfficer);
        mLoginComplainer = findViewById(R.id.BTNLoginComplainer);
        needhelpTV = findViewById(R.id.needhelpTV);
//        registerTv = findViewById(R.id.textViewRegister);

        //handle register button click
//        registerTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
//
//            }
//        });


        //handle login button click
        mLoginComplainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, complainerLogin.class));
            }
        });

        mLoginTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, technicianLogin.class));
            }
        });


        mLoginOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, officerLogin.class));
            }
        });

        needhelpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NeedHelpActivity.class));
            }
        });

    }
}
