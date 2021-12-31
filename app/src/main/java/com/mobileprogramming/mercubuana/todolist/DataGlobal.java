package com.mobileprogramming.mercubuana.todolist;

import com.mobileprogramming.mercubuana.todolist.model.Tugas;

import java.util.ArrayList;

public class DataGlobal {
    public ArrayList<Tugas> daftarTugas = new ArrayList<Tugas>();
    public static DataGlobal instance = null;
    public Tugas tugasDipilih;

    public static DataGlobal getInstance() {
        if (instance == null) {
            instance = new DataGlobal();
        }
        return instance;
    }
}
