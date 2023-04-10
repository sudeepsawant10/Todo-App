package com.sudeep.todoapp.home;


public class HomeCheckListModel {
    private long id;
    private String checkListTopicName, date;

    public HomeCheckListModel(long id, String checkListTopicName, String date) {
        this.id = id;
        this.checkListTopicName = checkListTopicName;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
