package com.example.pama.objects;

public class MissedCallItem {

    public String contactName;
    public String phoneNumber;

    public String callDate;

    public String callTime;

    public MissedCallItem() {
    }

    public MissedCallItem(String contactName, String phoneNumber, String callDate, String callTime) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.callDate = callDate;
        this.callTime = callTime;
    }

    public MissedCallItem(String contactName, String phoneNumber, String callTime) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.callTime = callTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }
}
