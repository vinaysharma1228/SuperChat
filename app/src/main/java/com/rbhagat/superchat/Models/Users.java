package com.rbhagat.superchat.Models;
public class Users {
    String profile_image,userName,userMail,uid,lastMessage,mobile,status;

    public Users(String profile_image, String userName, String userMail, String uid, String lastMessage,String mobile,String status) {
        this.profile_image = profile_image;
        this.userName = userName;
        this.userMail = userMail;
        this.uid = uid;
        this.lastMessage = lastMessage;
        this.mobile=mobile;
        this.status=status;
    }

    public Users()
    {

    }
    public Users(String mobile)

    {
        this.mobile=mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }



    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Users(String userName, String userMail, String profile_image, String uid)
    {

        this.userName=userName;
        this.userMail=userMail;
        this.profile_image=profile_image;
        this.uid=uid;

    }

    public String getUid(String key) {
        return uid;
    }

    public String getUID()
    {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }


//    public String getUid(String key) {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
