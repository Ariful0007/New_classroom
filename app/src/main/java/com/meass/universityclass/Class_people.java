package com.meass.universityclass;

public class Class_people {
    String name,image,contact,time,email;

    public String getName() {
        return name;
    }

    public Class_people() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Class_people(String name, String image, String contact, String time, String email) {
        this.name = name;
        this.image = image;
        this.contact = contact;
        this.time = time;
        this.email = email;
    }
}
