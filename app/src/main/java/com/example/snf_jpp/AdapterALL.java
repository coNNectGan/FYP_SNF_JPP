package com.example.snf_jpp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterALL extends RecyclerView.Adapter<AdapterALL.MyHolder> {

    // Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls
    // for application-level operations such as launching activities, broadcasting and receiving intents, etc.
    // **** complainertask (not edit yet)
    Context context;
    List<ModelTask> taskList;

    //lick listerner
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    //-----------------------

    //constructor
    public AdapterALL(Context context, List<ModelTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    //
    @NonNull
    @Override
    public AdapterALL.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row_task.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_all, viewGroup, false);
        //return the view
        return new AdapterALL.MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        //get data
        String uid = taskList.get(i).getUid();
        String uEmail = taskList.get(i).getuEmail();
        String uName = taskList.get(i).getuName();
        String uDp = taskList.get(i).getuDp();
        final String pId = taskList.get(i).getpId();
        final String pTitle = taskList.get(i).getpTitle();
        String pDescription = taskList.get(i).getpDescription();
        final String pImage = taskList.get(i).getpImage();
        String pTimeStamp = taskList.get(i).getpTime();
       final String worktype = taskList.get(i).getWorktype();
        final String status = taskList.get(i).getStatus();
        final String location = taskList.get(i).getLocation();
        final String descrip = taskList.get(i).getpDescription();

        //convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
//        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        final String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        //set data
        myHolder.uNameTv.setText(pTitle);
        myHolder.pTimeTv.setText(pTime);
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


       // set user dp
//        try {
//            Picasso.get().load(uDp).placeholder(R.drawable.ic_default_img).into(myHolder.uPictuceIv);
//        }
//        catch (Exception e) {
//
//        }

        //set post image
//        //if there is no image i.e. pImage.equals("noImage") then hide imageView
//        if(pImage.equals("noImage")){
//            //hide imageView
//            myHolder.pImageIv.setVisibility(View.GONE);
//
//        } else {
//
//            try {
//                Picasso.get().load(pImage).into(myHolder.pImageIv);
//            }
//            catch (Exception e) {
//
//            }
//.
//        }



        //intent code
        myHolder.parent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,allReportPage.class);

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

    @Override
    public int getItemCount() {
        //change 1
        return taskList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //view from row_post.xml
        ImageView pImageIv;
        TextView uNameTv, pTimeTv,pTitleTv,pDescriptionTv;
        CardView parent_card;
        ImageView statusLight;
        ImageView uPictuceIv;





        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
//            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pImageIv = itemView.findViewById(R.id.work_Image);
            uNameTv = itemView.findViewById(R.id.titleComplaint);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.titleComplaint);
            pDescriptionTv = itemView.findViewById(R.id.work_ID);
            parent_card = itemView.findViewById(R.id.parent_card);
            statusLight = itemView.findViewById(R.id.statuslight);
            uPictuceIv = itemView.findViewById(R.id.uPictureIv);

            //item click
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