package com.sudeep.todoapp.home;


public class HomeCheckListModel {
    private long checkListId;
    private String checkListTopicName;
    private String date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public HomeCheckListModel() {
    }

    public HomeCheckListModel(long checkListId, String checkListTopicName, String date) {
        this.checkListId = checkListId;
        this.checkListTopicName = checkListTopicName;
        this.date = date;
    }

    public long getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(long checkListId) {
        this.checkListId = checkListId;
    }

    public String getCheckListTopicName() {
        return checkListTopicName;
    }

    public void setCheckListTopicName(String checkListTopicName) {
        this.checkListTopicName = checkListTopicName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
