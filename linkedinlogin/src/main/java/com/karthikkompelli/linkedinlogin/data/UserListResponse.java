package com.karthikkompelli.linkedinlogin.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserListResponse {

    private FirstName firstName = null;
    private LastName lastName = null;
    private ProfilePicture profilePicture = null;
    private String id = null;

    public class FirstName {
        Localized localized = null;

        public Localized getLocalized() {
            return localized;
        }

        public void setLocalized(Localized localized) {
            this.localized = localized;
        }
    }

    public class Localized {
        @SerializedName("en_US")
        String englishUs = null;

        public String getEnglishUs() {
            return englishUs;
        }

        public void setEnglishUs(String englishUs) {
            this.englishUs = englishUs;
        }
    }

    public class LastName {
        Localized localized = null;

        public Localized getLocalized() {
            return localized;
        }

        public void setLocalized(Localized localized) {
            this.localized = localized;
        }
    }

    public class ProfilePicture {
        @SerializedName("displayImage~")
        DisplayImage displayImageUrl = null;

        public DisplayImage getDisplayImageUrl() {
            return displayImageUrl;
        }

        public void setDisplayImageUrl(DisplayImage displayImageUrl) {
            this.displayImageUrl = displayImageUrl;
        }
    }

    public class DisplayImage {
        @SerializedName("elements")
        ArrayList<Elements> elements = null;

        public ArrayList<Elements> getElements() {
            return elements;
        }

        public void setElements(ArrayList<Elements> elements) {
            this.elements = elements;
        }
    }

    public class Elements {
        ArrayList<Identifiers> identifiers = null;

        public ArrayList<Identifiers> getIdentifiers() {
            return identifiers;
        }

        public void setIdentifiers(ArrayList<Identifiers> identifiers) {
            this.identifiers = identifiers;
        }
    }

    public class Identifiers {
        String identifier = null;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstName firstName) {
        this.firstName = firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
