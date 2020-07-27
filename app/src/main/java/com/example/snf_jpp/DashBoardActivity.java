package com.example.snf_jpp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class DashBoardActivity extends AppCompatActivity {
//
//    //firebase auth
//    FirebaseAuth firebaseAuth;
//    ActionBar actionBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//
//        //Actionbar and its title
//        actionBar = getSupportActionBar();
//        actionBar.setTitle("Profile");
//
//        //init
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        //bottom navigation
//        BottomNavigationView navigationView = findViewById(R.id.navigation);
//        navigationView.setOnNavigationItemSelectedListener(selectedListener);
//
//        //home fragment transaction (default, on star)
//        actionBar.setTitle("Home"); // change action title
//        HomeFragment fragment1 = new HomeFragment();
//        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//        ft1.replace(R.id.content, fragment1, "");
//        ft1.commit();
//
//
//
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    //handle item click
//                    switch (item.getItemId()){
//                        case R.id.nav_home:
//                            //home fragment transaction
//                            actionBar.setTitle("Home"); // change action title
//                            HomeFragment fragment1 = new HomeFragment();
//                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                            ft1.replace(R.id.content, fragment1, "");
//                            ft1.commit();
//                            return true;
//
//                        case R.id.nav_profile:
//                            //profile fragment transaction
//                            //home fragment transaction
//                            actionBar.setTitle("Profile"); // change action title
//                            ProfileFragment fragment2 = new ProfileFragment();
//                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
//                            ft2.replace(R.id.content, fragment2, "");
//                            ft2.commit();
//                            return true;
//                        case R.id.nav_users:
//                            //users fragment transaction
//                            //home fragment transaction
//                            actionBar.setTitle("users"); // change action title
//                            UsersFragment fragment3 = new UsersFragment();
//                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
//                            ft3.replace(R.id.content, fragment3, "");
//                            ft3.commit();
//                            return true;
//                        case R.id.nav_task:
//                            //task fragment transaction
//                            //home fragment transaction
//                            actionBar.setTitle("Task"); // change action title
//                            TaskFragment fragment4 = new TaskFragment();
//                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
//                            ft4.replace(R.id.content, fragment4, "");
//                            ft4.commit();
//                            return true;
//
//                    }
//
//
//
//
//                    return false;
//                }
//            };
//
//    private void checkUserStatus(){
//        //get current user
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            //user is signed in stay here
//            //set email of logged in user
////            ProfileTv.setText(user.getEmail());
//        }
//        else {
//            //user not signed in, go to main activity
//            startActivity(new Intent(DashBoardActivity.this,MainActivity.class));
//            finish();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//    protected void onStart() {
//        //check on start of app
//        checkUserStatus();
//        super.onStart();
//    }
//
//
//}
