package com.example.collegeevent.Lokesh;
public class Permission {
    private String name, group, desc, type, pdf;
    private String userID;
    private String channelApproved;
    public Permission() {
    }

    public Permission(String name, String group, String desc, String type, String pdf, String userID, String channelApproved) {
        this.name = name;
        this.group = group;
        this.desc = desc;
        this.type = type;
        this.pdf = pdf;
        this.userID = userID;
        this.channelApproved = channelApproved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getChannelApproved() {
        return channelApproved;
    }

    public void setChannelApproved(String channelApproved) {
        this.channelApproved = channelApproved;
    }


}