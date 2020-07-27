package com.example.snf_jpp.complainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.snf_jpp.MainActivity;
import com.example.snf_jpp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class complainerDashBoard extends AppCompatActivity {

    //firebase auth
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;

    //sharepreferences keep user logged
//    SharedPreferences sharedPreferences;
//    public static final String filneName = "login";
//    public static final String StaffID = "StaffID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainer_dash_board);

        //-- keep user logged
//        sharedPreferences = getSharedPreferences(filneName, Context.MODE_PRIVATE);

        //Actionbar and its title
        actionBar = getSupportActionBar();
//        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //bottom navigation
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //home fragment transaction (default, on star)
//        actionBar.setLogo(R.mipmap.actionlogobar);
//
//
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        actionBar.setTitle("SnF JPP"); // change action title
        complainerHomeFragment fragment1 = new complainerHomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();

    }

    //BotNavigation selectedListener
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //handle item click
                    switch (item.getItemId()){
                        case R.id.nav_homeC:
                            //home fragment transaction
                            actionBar.setTitle("SnF JPP"); // change action title
                            complainerHomeFragment fragment1 = new complainerHomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, fragment1, "");
                            ft1.commit();
                            return true;


                        case R.id.nav_complaintC:
                            //users fragment transaction
                            //home fragment transaction
                            actionBar.setTitle("Search"); // change action title
                            complainerComplaintFragment fragment3 = new complainerComplaintFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, fragment3, "");
                            ft3.commit();
                            return true;

                        case R.id.nav_profileC:
                            //profile fragment transaction
                            //home fragment transaction
                            actionBar.setTitle("Me"); // change action title
                            complainerProfileFragment fragment2 = new complainerProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            return true;
//                        case R.id.nav_task:
//                            //task fragment transaction
//                            //home fragment transaction
//                            actionBar.setTitle("Task"); // change action title
//                            TaskFragment fragment4 = new TaskFragment();
//                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
//                            ft4.replace(R.id.content, fragment4, "");
//                            ft4.commit();
//                            return true;

                    }




                    return false;
                }
            };

    //check User Status
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
            startActivity(new Intent(complainerDashBoard.this, MainActivity.class));
            finish();
        }
    }

    //back button
    @Override
    public void onBackPressed() {
        //--
    }

    //execute checkuserstatus when start
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }
}
