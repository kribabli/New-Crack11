package com.example.yoyoiq.Model;

public class UserData {
    String userName;
    String mobileNo;
    String emailId;
    String user_id;
    String referral_code;

    public UserData(String userName, String mobileNo, String emailId, String user_id, String referral_code) {
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.user_id = user_id;
        this.referral_code = referral_code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userName='" + userName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", user_id='" + user_id + '\'' +
                ", referral_code='" + referral_code + '\'' +
                '}';
    }
}
