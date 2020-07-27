package com.example.snf_jpp.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.snf_jpp.MainActivity;
import com.example.snf_jpp.R;
import com.example.snf_jpp.complainer.complainerComplaintFragment;
import com.example.snf_jpp.complainer.complainerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class technicianDashBoard extends AppCompatActivity {
    private static final String Tag = "technicianDashBoard";
    //firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_dash_board);

        Log.d(Tag, "got intent");

        //Actionbar and its title
        actionBar = getSupportActionBar();

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //bottom navigation
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //home fragment transaction (default, on star)
        actionBar.setTitle("SnF JPP"); // change action title
        technicianHomeFragment fragment1 = new technicianHomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //handle item click
                    switch (item.getItemId()){
                        case R.id.nav_homeC:
                            //home fragment transaction
                            actionBar.setTitle("Home"); // change action title
                            technicianHomeFragment fragment1 = new technicianHomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, fragment1, "");
                            ft1.commit();
                            return true;

                        case R.id.nav_complaintC:
                            //profile fragment transaction
                            //home fragment transaction
//                            actionBar.setTitle("Search"); // change action title
                            complainerComplaintFragment fragment2 = new complainerComplaintFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            return true;
                        case R.id.nav_profileC:
                            //users fragment transaction
                            //home fragment transaction
                            actionBar.setTitle("Me"); // change action title
                            complainerProfileFragment fragment3 = new complainerProfileFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, fragment3, "");
                            ft3.commit();
                            return true;

                    }




                    return false;
                }
            };

    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            //set email of logged in user
//            ProfileTv.setText(user.getEmail());
        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(technicianDashBoard.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }


}
