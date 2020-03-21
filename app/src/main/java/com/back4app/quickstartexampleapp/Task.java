package com.back4app.quickstartexampleapp;

public class Task {
    private String objectId, task, memberName,memberId, adminId;
    private boolean isCompleted;

    public Task(String objectId, String task, String memberName,String memberId, String adminId, boolean isCompleted) {
        this.objectId = objectId;
        this.task = task;
        this.memberName = memberName;
        this.memberId = memberId;
        this.adminId = adminId;
        this.isCompleted = isCompleted;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
