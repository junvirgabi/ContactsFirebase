package com.example.gvg.contactsfirebase;

/**
 * Created by gvg on 8/3/2016.
 */
public class Contacts {

    private String contactNumber;
    private String friendName;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Contacts(String friendName, String contactNumber) {

        this.friendName = friendName;
        this.contactNumber = contactNumber;
    }

    public Contacts() {
    }
}
