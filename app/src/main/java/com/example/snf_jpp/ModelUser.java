package com.example.snf_jpp;

public class ModelUser {

    //use same name as in firebase database
    String staffid,position,phone,name,image,email,cover,uid;

    public ModelUser(){

    }

    public ModelUser(String staffid, String position, String phone, String name, String image, String email, String cover, String uid) {
        this.staffid = staffid;
        this.position = position;
        this.phone = phone;
        this.name = name;
        this.image = image;
        this.email = email;
        this.cover = cover;
        this.uid = uid;
    }

    public String getStaffid() {
        return staffid;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getCover() {
        return cover;
    }

    public String getUid() {
        return uid;
    }


}
