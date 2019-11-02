package com.karthikkompelli.linkedinlogin.data;

import android.os.Parcel;
import android.os.Parcelable;

public class LinkedInUserDetails implements Parcelable {
    private String firstName = null;
    private String lastName = null;
    private String image = null;
    private String email = null;

    public LinkedInUserDetails(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        email = in.readString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedInUserDetails() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(image);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LinkedInUserDetails> CREATOR = new Creator<LinkedInUserDetails>() {
        @Override
        public LinkedInUserDetails createFromParcel(Parcel in) {
            return new LinkedInUserDetails(in);
        }

        @Override
        public LinkedInUserDetails[] newArray(int size) {
            return new LinkedInUserDetails[size];
        }
    };
}
