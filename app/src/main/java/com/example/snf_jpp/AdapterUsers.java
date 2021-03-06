package com.example.snf_jpp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {

    Context context;
    List<ModelUser> userList;

    //constuctor


    public AdapterUsers(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       //inflate layout(row_user.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
           //get data
           String userImage = userList.get(i).getImage();
           String userName = userList.get(i).getName();
           final String userStaffID = userList.get(i).getStaffid();
           String userPosition = userList.get(i).getPosition();

           //set data
            myHolder.mNameTv.setText(userName);
            myHolder.mStaffidTv.setText(userStaffID);
            myHolder.mPositionTv.setText(userPosition);
            try {
                Picasso.get().load(userImage)
                        .placeholder(R.drawable.ic_default_img)
                        .into(myHolder.mAvatarIv);
            }
            catch (Exception e) {

            }

        //handle item click
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+userStaffID,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv,mStaffidTv,mPositionTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mStaffidTv = itemView.findViewById(R.id.staffidTv);
            mPositionTv = itemView.findViewById(R.id.positionTv);


        }
    }
}
