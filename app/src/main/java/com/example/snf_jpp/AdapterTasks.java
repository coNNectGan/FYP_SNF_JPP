package com.example.snf_jpp;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import java.text.DateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
//A ViewHolder describes an item view and metadata about its place within the RecyclerView
//RecyclerView.Adapter implementations should subclass ViewHolder and add fields for caching potentially expensive findViewById(int) results.
public class AdapterTasks extends RecyclerView.Adapter<AdapterTasks.MyHolder> {

    // Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls
    // for application-level operations such as launching activities, broadcasting and receiving intents, etc.
    Context context;
    List<ModelTask> taskList;

    //constructor
    public AdapterTasks(Context context, List<ModelTask> taskList) {
        this.context = context;
        this.taskList = taskList;
    }
   //
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row_task.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_tasks, viewGroup, false);
        //return the view
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        String uid = taskList.get(i).getUid();
        String uEmail = taskList.get(i).getuEmail();
        String uName = taskList.get(i).getuName();
        String uDp = taskList.get(i).getuDp();
        String pId = taskList.get(i).getpId();
        String pTitle = taskList.get(i).getpTitle();
        String pDescription = taskList.get(i).getpDescription();
        String pImage = taskList.get(i).getpImage();
        String pTimeStamp = taskList.get(i).getpTime();

        //convert timestamp to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        //set data
        myHolder.uNameTv.setText(uName);
        myHolder.pTimeTv.setText(pTime);
//        myHolder.pTitleTv.setText(pTitle);
//        myHolder.pDescriptionTv.setText(pDescription);


        //set user dp
//        try {
//            Picasso.get().load(uDp).placeholder(R.drawable.ic_default_img).into(myHolder.uPictureIv);
//        }
//        catch (Exception e) {
//
//        }

        //set post image
        //if there is no image i.e. pImage.equals("noImage") then hide imageView
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
//
//        }


        //handle button clicks
//        myHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //will implement later
//                Toast.makeText(context,"More", Toast.LENGTH_SHORT).show();
//
//            }
//        });



    }
//*
    @Override
    public int getItemCount() {
        //change 1
        return taskList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //view from row_post.xml
//        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv,pTitleTv,pDescriptionTv,pTag;
//        ImageButton moreBtn;
//        Button reportBTN;




        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
//            uPictureIv = itemView.findViewById(R.id.uPictureIv);
//            pImageIv = itemView.findViewById(R.id.pImageIv);
//            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.titleComplaint);
//            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
//            pTag = itemView.findViewById(R.id.pTagTv);



        }
    }
}
