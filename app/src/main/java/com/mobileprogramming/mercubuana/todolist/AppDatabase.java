package com.mobileprogramming.mercubuana.todolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mobileprogramming.mercubuana.todolist.dao.TugasDAO;
import com.mobileprogramming.mercubuana.todolist.model.Tugas;

@Database(entities = {Tugas.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TugasDAO getTugasDAO();
}
