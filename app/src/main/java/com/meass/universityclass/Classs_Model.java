package com.meass.universityclass;

public class Classs_Model {
    String name,section,roomid,class_,uuid,time,email,userid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Classs_Model(String name, String section,
                        String roomid, String class_, String uuid, String time, String email, String userid) {
        this.name = name;
        this.section = section;
        this.roomid = roomid;
        this.class_ = class_;
        this.uuid = uuid;
        this.time = time;
        this.email = email;
        this.userid = userid;
    }

    public Classs_Model() {
    }
}
