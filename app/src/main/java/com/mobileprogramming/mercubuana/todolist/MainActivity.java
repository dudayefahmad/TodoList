package com.mobileprogramming.mercubuana.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.mobileprogramming.mercubuana.todolist.dao.TugasDAO;
import com.mobileprogramming.mercubuana.todolist.model.Tugas;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataGlobal dataGlobal = DataGlobal.getInstance();
    RecyclerView rvDaftarTugas;
    EditText editTextNamaTugasBaru;
    Button btnRekam;
    TextView tvTugasKosong;
    TugasAdapter adapter;
    ActivityResultLauncher<Intent> resultLauncher;
    AppDatabase database;
    TugasDAO tugasDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        membuatDatabaseTugas();
        initListener();
        bacaDataDenganDatabase();
        // get result for activity result
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            updateDataDenganDatabase(dataGlobal.tugasDipilih);
            updateTampilanListView();
        });
    }

    private void initListener() {
        btnRekam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNamaTugasBaru.getText().toString().isEmpty()) {
                    rekamDataTugasBaru();
                    editTextNamaTugasBaru.setText("");
                    hideKeybaord(v);
                } else {
                    Toast.makeText(v.getContext(), "Tugas harus di isi terlebih dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        btnRekam = findViewById(R.id.btnRekam);
        editTextNamaTugasBaru = findViewById(R.id.edtNamaTugasBaru);
        rvDaftarTugas = findViewById(R.id.rvDaftarTugas);
        tvTugasKosong = findViewById(R.id.tvTugasKosong);
    }

    private void membuatDatabaseTugas() {
        database = Room.databaseBuilder(this, AppDatabase.class, "tugasdb")
                .allowMainThreadQueries()
                .build();
    }

    private void bacaDataDenganDatabase() {
        tugasDAO = database.getTugasDAO();
        DataGlobal.getInstance().daftarTugas = (ArrayList<Tugas>) tugasDAO.getTugas();
        updateTampilanListView();
    }

    private void rekamDataTugasBaru() {

        String namaTugasBaru = editTextNamaTugasBaru.getText().toString();
        Tugas tugasBaru = new Tugas();
        tugasBaru.setNamaTugas(namaTugasBaru);
        dataGlobal.daftarTugas.add(tugasBaru);
        updateTampilanListView();
        tambahDataDenganDatabase(tugasBaru);
    }

    private void lengkapiDataTugas(int position, Tugas namaTugasDicari) {
        Intent formDetailDataTugas = new Intent(this, DetailTugasActivity.class);
        dataGlobal.tugasDipilih = dataGlobal.daftarTugas.get(position);
        resultLauncher.launch(formDetailDataTugas);
    }

    private void hapusTugas(Tugas namaTugasDicari) {
        int indeksTugasDicari = dataGlobal.daftarTugas.indexOf(namaTugasDicari);
        Tugas tugasDicari = dataGlobal.daftarTugas.get(indeksTugasDicari);
        hapusDenganDatabase(tugasDicari);
        dataGlobal.daftarTugas.remove(indeksTugasDicari);
        updateTampilanListView();
    }

    private void updateTampilanListView() {
        if (dataGlobal.daftarTugas.size() <= 0) {
            tvTugasKosong.setVisibility(View.VISIBLE);
            rvDaftarTugas.setVisibility(View.GONE);
        } else {
            tvTugasKosong.setVisibility(View.GONE);
            rvDaftarTugas.setVisibility(View.VISIBLE);
        }
        rvDaftarTugas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TugasAdapter(dataGlobal.daftarTugas, this);
        rvDaftarTugas.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void hapusTugasListener(int position, Tugas tugas) {
                hapusTugas(tugas);
            }

            @Override
            public void lengkapiDataTugasListener(int position, Tugas tugas) {
                lengkapiDataTugas(position, tugas);
            }
        });
    }

    private void tambahDataDenganDatabase(Tugas tugasBaru) {
        tugasDAO.insert(tugasBaru);
        Long id = tugasDAO.getTugasByNamaTugas(tugasBaru.getNamaTugas()).getId();
        tugasBaru.setId(id);
    }

    private void hapusDenganDatabase(Tugas tugasDicari) {
        tugasDAO.delete(tugasDicari);
    }

    private void updateDataDenganDatabase(Tugas tugasDipilih) {
        tugasDAO.update(tugasDipilih);
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
}