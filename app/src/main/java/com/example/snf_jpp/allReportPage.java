package com.example.snf_jpp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class allReportPage extends AppCompatActivity {

    private static final String Tag = "complaintReportPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_report_page);

        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Report Page");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


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


    public boolean onSupportNavigateUp() {
        onBackPressed(); // go previous activity
        return super.onSupportNavigateUp();
    }
}
