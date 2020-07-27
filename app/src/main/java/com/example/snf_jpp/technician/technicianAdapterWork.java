package com.example.snf_jpp.technician;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snf_jpp.ModelTask;
import com.example.snf_jpp.R;
import com.example.snf_jpp.complainer.complaintReportPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class technicianAdapterWork extends RecyclerView.Adapter<technicianAdapterWork.MyHolder> {

    private static final String TAG = "RecycleViewAdapter666";
    // Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls
    // for application-level operations such as launching activities, broadcasting and receiving intents, etc.
    // **** complainertask (not edit yet)
    Context context;
    List<ModelTask> taskList;

    //-----------------------------------------
    private technicianAdapterWork.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(technicianAdapterWork.OnItemClickListener listener){
        mListener = listener;
    }
    //------------------------------------------

    //constructor
    public technicianAdapterWork(Context context, List<ModelTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    //
    @NonNull
    @Override
    public technicianAdapterWork.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row_task.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_complaint, viewGroup, false);
        //return the view
        return new technicianAdapterWork.MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final technicianAdapterWork.MyHolder myHolder, final int i) {

        //get data
        final String uid = taskList.get(i).getUid();
        String uEmail = taskList.get(i).getuEmail();
        String uName = taskList.get(i).getuName();
        String uDp = taskList.get(i).getuDp();
        final String pId = taskList.get(i).getpId();
        final String pTitle = taskList.get(i).getpTitle();
        String pDecor = taskList.get(i).getpDescription();
        final String pImage = taskList.get(i).getpImage();
        String pTimeStamp = taskList.get(i).getpTime();
        final String status = taskList.get(i).getStatus();
        final String worktype = taskList.get(i).getWorktype();
        final String location = taskList.get(i).getLocation();
        final String descrip = taskList.get(i).getpDescription();

        String statusLight1 = taskList.get(i).getStatusLight();

        //convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
//        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        final String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
     if(user != null) {
         String uiduser = user.getEmail();
         if(uiduser == uEmail ) {

             //set data
//        myHolder.uNameTv.setText(uName);
             myHolder.pTimeTv.setText(pTime);
             myHolder.pTitleTv.setText(pTitle);
//        myHolder.descriptionComplaint.setText(pId);
             myHolder.workType.setText(worktype);
             myHolder.location.setText(location);
//        myHolder.descrip.setText(status);

// status change
             String statusAvailable = "Available";
             String statusOnGoing ="Ongoing";
             String statusFixed = "Fixed";
             String statusApprovedOfficer = "Approved by Officer";
             String statusApprovedComplainer = "Approved by Complainer";
//problem
             if(statusAvailable.equals(status)){
                 //hide imageView
                 myHolder.statusLight.setBackgroundColor(Color.parseColor("#FF0000"));
//            Picasso.get().load(statusLight1).placeholder(R.drawable.ic_default_img).into(myHolder.statusLight);
             } else if (statusOnGoing.equals(status)){
                 myHolder.statusLight.setBackgroundColor(Color.YELLOW);
             }else if (statusFixed.equals(status)){
                 myHolder.statusLight.setBackgroundColor(Color.GREEN);
             }else if (statusApprovedComplainer.equals(status)){
                 myHolder.statusLight.setBackgroundColor(Color.BLUE);
             }else if (statusApprovedOfficer.equals(status)){
                 myHolder.statusLight.setBackgroundColor(Color.parseColor("#800080"));
             }


             //set post image
             //if there is no image i.e. pImage.equals("noImage") then hide imageView
             if(pImage.equals("noImage")){
                 //hide imageView
                 myHolder.pImageIv.setVisibility(View.GONE);

             } else {

                 try {
                     Picasso.get().load(pImage).into(myHolder.pImageIv);
                 }
                 catch (Exception e) {

                 }

             }

//intent code

             myHolder.parent_card.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Log.d(TAG,"onClick:clicked");
                     Intent intent = new Intent(context, complaintReportPage.class);

                     intent.putExtra("complaintID",pId);
                     intent.putExtra("complaintIMAGE",pImage);
                     intent.putExtra("complaintTitle",pTitle);
                     intent.putExtra("complaintTime",pTime);
                     intent.putExtra("complaintWorkType",worktype);
                     intent.putExtra("complaintStatus",status);
                     intent.putExtra("complaintLocation",location);
                     intent.putExtra("complaintDescription",descrip);


                     context.startActivity(intent);



                 }
             });

         }

     }










    }
//


    @Override
    public int getItemCount() {
        //change 1
        return taskList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //view from row_post.xml
        ImageView pImageIv;
        TextView  pTimeTv,pTitleTv;
        TextView descriptionComplaint,ds;
        TextView workType,location;
        LinearLayout idBar;

        ImageView statusLight;

        CardView parent_card;



        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
//            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pImageIv = itemView.findViewById(R.id.work_Image);
//            uNameTv = itemView.findViewById(R.id.titleComplaint);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.titleComplaint);
            descriptionComplaint = itemView.findViewById(R.id.work_ID);
//            ds = itemView.findViewById(R.id.ds);
            idBar = itemView.findViewById(R.id.idBar);
            //new
            workType = itemView.findViewById(R.id.worktype);
            location = itemView.findViewById(R.id.location);
//            descrip = itemView.findViewById(R.id.descriptionshow);

            statusLight = itemView.findViewById(R.id.statuslight);

            //cardview
            parent_card = itemView.findViewById(R.id.parent_cardVirw);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });



        }

    }



}
