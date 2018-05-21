package com.example.alexandrareinhart.taskmanagerapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    //get all tasks
    @Query("SELECT * FROM task")
    List<Task> getTasks();

    //add a single task to list
    @Insert
    void addTask(Task task);

    //edit and update values of existing task
    @Update
    void updateTask(Task task);

    //delete task from list
    @Delete
    void deleteTask(Task task);
}
