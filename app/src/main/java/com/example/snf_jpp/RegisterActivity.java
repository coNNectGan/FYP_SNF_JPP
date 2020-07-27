package com.example.snf_jpp;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

//    //views
//    EditText StaffidED, PasswordED;
//    Button registerBTN;
//    TextView HaveAccountTv;
//
//    //progressbar to display while registering user
//    ProgressDialog progressDialog;
//
//    //Declare an instance of FirebaseAuth
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        //Actionbar and its title
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Create Account");
//        //enable back button
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        //initial
//        StaffidED = findViewById(R.id.staffidET);
//        PasswordED = findViewById(R.id.passwordET);
//        registerBTN = findViewById(R.id.BTNRegister);
//        HaveAccountTv = findViewById(R.id.HaveLoginText);
//
//
//        //In the onCreate() method, initialize the FirebaseAuth instance.
//        mAuth = FirebaseAuth.getInstance();
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Registering User...");
//
//        //handle register btn click
//        registerBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //input staffID,password
//                String StaffID = StaffidED.getText().toString().trim();
//                String Password = PasswordED.getText().toString().trim();
//                //validation
//                if (!Patterns.EMAIL_ADDRESS.matcher(StaffID).matches()) {
//                    StaffidED.setError("Invalid StaffID");
//                    StaffidED.setFocusable(true);
//                } else {
//                    registerUser(StaffID, Password);
//                }
//
//            }
//        });
//        //handle login textview click listener
//        HaveAccountTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
//                finish();
//            }
//        });
//
//
//
//    }
//
//    private void registerUser(String StaffID, String Password) {
//        progressDialog.show();
//        mAuth.createUserWithEmailAndPassword(StaffID, Password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, dismiss dialog and start register activity
//                            progressDialog.dismiss();
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            //Get user emain and uid from auth
//                            String staffid = user.getEmail();
//                            String uid = user.getUid();
//                            //when user is registered store user info in firebase realtime database too
//
//                            //using HashMap
//                            HashMap<Object,String> hashMap = new HashMap<>();
//                            //put info in hashmap
//                            hashMap.put("staffid",staffid);
//                            hashMap.put("uid",uid);
//                            hashMap.put("position","");
//                            hashMap.put("name",""); // add later
//                            hashMap.put("email","");
//                            hashMap.put("phone","");// add later
//                            hashMap.put("image","");// add later
//                            hashMap.put("cover","");// add later
//
//                            //firebase database instance
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            //path to store user data named "users"
//                            DatabaseReference reference = database.getReference("Users");
//                            //put data within hashmap in database
//                            reference.child(uid).setValue(hashMap);
//
//
//
//                            Toast.makeText(RegisterActivity.this, "register...\n" + user.getEmail(), Toast.LENGTH_SHORT);
////                            startActivity(new Intent(RegisterActivity.this, DashBoardActivity.class));
//                            finish();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            progressDialog.dismiss();
//
//                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                //error, dismiss progressdialog and get and show the error message
//                progressDialog.dismiss();
//                Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed(); // go previous activity
//        return super.onSupportNavigateUp();
//    }
}
