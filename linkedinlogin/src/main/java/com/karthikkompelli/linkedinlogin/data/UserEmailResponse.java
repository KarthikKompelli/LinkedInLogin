package com.karthikkompelli.linkedinlogin.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserEmailResponse {

    ArrayList<Elements> elements = null;

    public class Elements {
        @SerializedName("handle~")
        Handle handleData = null;

        public Handle getHandleData() {
            return handleData;
        }

        public void setHandleData(Handle handleData) {
            this.handleData = handleData;
        }
    }

    public class Handle {
        String emailAddress = null;

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
    }

    public ArrayList<Elements> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elements> elements) {
        this.elements = elements;
    }
}
