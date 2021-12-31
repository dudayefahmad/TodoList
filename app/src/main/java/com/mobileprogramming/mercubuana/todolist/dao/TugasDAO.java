package com.mobileprogramming.mercubuana.todolist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobileprogramming.mercubuana.todolist.model.Tugas;

import java.util.List;

@Dao
public interface TugasDAO {
    @Insert
    public void insert(Tugas... tugas);

    @Update
    public void update(Tugas... tugas);

    @Delete
    public void delete(Tugas tugas);

    @Query("SELECT * FROM tugas")
    public List<Tugas> getTugas();

    @Query("SELECT * FROM tugas WHERE id = :id")
    public Tugas getTugasById(Long id);

    @Query("SELECT * FROM tugas WHERE nama_tugas = :namaTugas")
    public Tugas getTugasByNamaTugas(String namaTugas);
}
