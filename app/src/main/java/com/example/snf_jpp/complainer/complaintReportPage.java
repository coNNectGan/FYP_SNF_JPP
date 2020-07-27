package com.example.snf_jpp.complainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.snf_jpp.R;

public class complaintReportPage extends AppCompatActivity  {

    private static final String Tag = "complaintReportPage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_report_page);
        Log.d(Tag,"onCreate:Started");

        getIncomingIntent();


    }
    private void getIncomingIntent(){
        Log.d(Tag,"check incoming intent");

        if(getIntent().hasExtra("complaintID")){
            Log.d(Tag,"got intent");

            String complaintID = getIntent().getStringExtra("complaintID");
            String complaintIMAGE = getIntent().getStringExtra("complaintIMAGE");
            String complaintTitle = getIntent().getStringExtra("complaintTitle");
            String complaintTime = getIntent().getStringExtra("complaintTime");
            String complaintWorkType = getIntent().getStringExtra("complaintWorkType");
            String complaintStatus = getIntent().getStringExtra("complaintStatus");
            String complaintLocation = getIntent().getStringExtra("complaintLocation");
            String complaintDescription = getIntent().getStringExtra("complaintDescription");


            setInfo(complaintID,complaintIMAGE,complaintTitle,complaintTime,complaintWorkType,complaintStatus,complaintLocation,complaintDescription);


            ImageView statusLight = findViewById(R.id.statuslight);
            // status change
            String statusAvailable = "Available";
            String statusOnGoing ="Ongoing";
            String statusFixed = "Fixed";
            String statusApprovedOfficer = "Approved by Officer";
            String statusApprovedComplainer = "Approved by Complainer";
//problem
            if(statusAvailable.equals(complaintStatus)){
                //hide imageView
                statusLight.setBackgroundColor(Color.parseColor("#FF0000"));
//            Picasso.get().load(statusLight1).placeholder(R.drawable.ic_default_img).into(myHolder.statusLight);
            } else if (statusOnGoing.equals(complaintStatus)){
               statusLight.setBackgroundColor(Color.YELLOW);
            }else if (statusFixed.equals(complaintStatus)){
                statusLight.setBackgroundColor(Color.GREEN);
            }else if (statusApprovedComplainer.equals(complaintStatus)){
                statusLight.setBackgroundColor(Color.BLUE);
            }else if (statusApprovedOfficer.equals(complaintStatus)){
                statusLight.setBackgroundColor(Color.parseColor("#800080"));
            }
        }



    }

    private  void setInfo(String complaintID,String complaintIMAGE,String complaintTitle,String complaintTime,String complaintWorkType,String complaintStatus,String complaintLocation, String complaintDescription){
        Log.d(Tag,"setinfo into the page");

        TextView workID = findViewById(R.id.work_ID);
        workID.setText(complaintID);

        ImageView workImage = findViewById(R.id.work_Image);
        Glide.with(this)
                .asBitmap()
                .load(complaintIMAGE)
                .into(workImage);

        TextView workTitle = findViewById(R.id.titleComplaint);
        workTitle.setText(complaintTitle);

        TextView workTime = findViewById(R.id.pTimeTv);
        workTime.setText(complaintTime);

        TextView workType = findViewById(R.id.worktype);
        workType.setText(complaintWorkType);

        TextView workStatus = findViewById(R.id.status);
        workStatus.setText(complaintStatus);

        TextView workLocation = findViewById(R.id.location);
        workLocation.setText(complaintLocation);

        TextView workDescription = findViewById(R.id.desComplainer);
        workDescription.setText(complaintDescription);



    }


    //
//    //firebase auth
//    FirebaseAuth firebaseAuth;
//    RecyclerView recyclerView;
//    List<ModelTask> taskList;
//    complaintReportPageAdapter complaintreportPageAdapter;
//    ProgressBar progressBar;
//
//    String STR_ID;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_complaint_report_page);
//
//
////init
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        //recycler view and its properties
//        recyclerView = findViewById(R.id.ReportpageRecyclerview);
//        progressBar = findViewById(R.id.progress_bar);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(complaintReportPage.this);
//
//        //set layout to recyclerview
//        recyclerView.setLayoutManager(layoutManager);
//
//        //init task list
//        taskList = new ArrayList<>();
//        //loadTask
//        loadTasks();
//
//
//
//    }
//
//    private void loadTasks() {
//        //path of all tasks
//        //A Firebase reference represents a particular location in your Database and can be used for reading or writing data to that Database location.
//        //The entry point for accessing a Firebase Database. You can get an instance by calling getInstance(). To access a location in the database and read or write data, use getReference().
//        //the key word task
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tasks");
//
//        //get all data from this ref
//        //Classes implementing this interface can be used to receive events about data changes at a location.
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                taskList.clear();
//                //A DataSnapshot instance contains data from a Firebase Database location. Any time you read Database data, you receive the data as a DataSnapshot.
//                for (DataSnapshot ds:dataSnapshot.getChildren()){
//
//                    //get value into modelTask from ds(data)
//                    ModelTask modelTask = ds.getValue(ModelTask.class);
//
//                    //add modeltask value/data into task list
//                    taskList.add(modelTask);
//
//                    complaintreportPageAdapter = new complaintReportPageAdapter(complaintReportPage.this,taskList);
//                    // set adapter to recyclerview
//                    recyclerView.setAdapter(complaintreportPageAdapter);
//
//
//                    //------------
//                    complaintreportPageAdapter.setOnItemClickListener(complaintReportPage.this);
//
//                    complaintreportPageAdapter.notifyDataSetChanged();
//                    recyclerView.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //in case of error
//                //dont know why error
////                Toast.makeText(getActivity(),"error"+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//    private void checkUserStatus(){
//        //get current user
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            //user is signed in stay here
//            //set email of logged in user
////            ProfileTv.setText(user.getEmail());
//            Toast.makeText(complaintReportPage.this,"Hello Javatpoint",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            //user not signed in, go to main activity
//            startActivity(new Intent(complaintReportPage.this,MainActivity.class));
//            complaintReportPage.this.finish();
//        }
//    }
//
//    @Override
//    public void onItemClick(int position) {
//
//    }
}
