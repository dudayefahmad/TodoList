package com.mobileprogramming.mercubuana.todolist.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tugas")
public class Tugas {
    @PrimaryKey
    @NonNull private Long id;
    @ColumnInfo(name = "nama_tugas")
    private String namaTugas;
    @ColumnInfo(name = "tanggal_tugas")
    private String tanggalTugas;
    @ColumnInfo(name = "jam_tugas")
    private String jamTugas;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getNamaTugas() {
        return namaTugas;
    }

    public void setNamaTugas(String namaTugas) {
        this.namaTugas = namaTugas;
    }

    public String getTanggalTugas() {
        return tanggalTugas;
    }

    public void setTanggalTugas(String tanggalTugas) {
        this.tanggalTugas = tanggalTugas;
    }

    public String getJamTugas() {
        return jamTugas;
    }

    public void setJamTugas(String jamTugas) {
        this.jamTugas = jamTugas;
    }
}
