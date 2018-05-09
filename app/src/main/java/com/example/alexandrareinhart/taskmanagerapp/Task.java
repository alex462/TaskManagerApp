package com.example.alexandrareinhart.taskmanagerapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskTitle;
    private String taskDetails;
    private boolean isPriority;
    private Date date;
    private boolean isCompleted;

    public Task(String taskTitle, String taskDetails, Date date) {
        this.taskTitle = taskTitle;
        this.taskDetails = taskDetails;
        this.date = date;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
