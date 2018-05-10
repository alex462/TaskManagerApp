package com.example.alexandrareinhart.taskmanagerapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Task.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
