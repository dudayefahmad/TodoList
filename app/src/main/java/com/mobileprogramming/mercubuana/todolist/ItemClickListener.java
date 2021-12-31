package com.mobileprogramming.mercubuana.todolist;

import com.mobileprogramming.mercubuana.todolist.model.Tugas;

public interface ItemClickListener {
    void hapusTugasListener(int position, Tugas tugas);
    void lengkapiDataTugasListener(int position, Tugas tugas);
}
