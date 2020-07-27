package com.example.snf_jpp;

import  android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        loadingScreen();
    }

    private void loadingScreen(){
        progressStatus = 0;
        new Thread(new Runnable()
        {
            public void run(){
                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus += 1;
                    //Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    //Update the progress bar

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                }

                //Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class)
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start(); // start the operation
    }
}
