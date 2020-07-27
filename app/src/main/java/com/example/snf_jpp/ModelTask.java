package com.example.snf_jpp;

public class ModelTask {
    //use same name as we given while uploading post
    String pId, pTitle, pDescription, pImage, pTime, uid, uEmail, uDp, uName,statusLight,status,location,worktype, commentTechnician,commentOfficer;

    public ModelTask() {

    }


    public String getCommentTechnician() {
        return commentTechnician;
    }

    public String getCommentOfficer() {
        return commentOfficer;
    }

    public void setCommentTechnician(String commentTechnician) {
        this.commentTechnician = commentTechnician;
    }

    public void setCommentOfficer(String commentOfficer) {
        this.commentOfficer = commentOfficer;
    }

    public ModelTask(String pId, String pTitle, String pDescription, String pImage, String pTime, String uid, String uEmail, String uDp, String uName, String statusLight, String status, String commentTechnician, String commentOfficer) {
        this.pId = pId;
        this.pTitle = pTitle;

        this.pDescription = pDescription;

        this.pImage = pImage;
        this.pTime = pTime;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uDp = uDp;
        this.uName = uName;
        this.statusLight = statusLight;
        this.status = status;
        this.location = location;
        this.worktype = worktype;
        this.commentTechnician = commentTechnician;
        this.commentOfficer = commentOfficer;
    }

    public String getpId() {
        return pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public String getpImage() {
        return pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public String getUid() {
        return uid;
    }

    public String getuEmail() {
        return uEmail;
    }

    public String getuDp() {
        return uDp;
    }

    public String getuName() {
        return uName;
    }

    public String getStatusLight() {
        return statusLight;
    }

    public String getStatus() {
        return status;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public void setpDescription(String pDescr) {
        this.pDescription = pDescr;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setStatusLight(String statusLight) {
        this.statusLight = statusLight;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }
}
