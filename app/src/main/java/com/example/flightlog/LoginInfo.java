package com.example.flightlog;

public class LoginInfo {
    private String Name;
    private String Password;
    private String EmailId;
    private String Designation;

    public LoginInfo(String name, String password, String emailId, String designation, String phoneNumber) {
        Name = name;
        Password = password;
        EmailId = emailId;
        Designation = designation;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private String PhoneNumber;
}
