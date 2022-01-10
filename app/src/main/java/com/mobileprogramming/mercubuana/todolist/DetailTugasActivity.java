package com.mobileprogramming.mercubuana.todolist;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mobileprogramming.mercubuana.todolist.model.Tugas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailTugasActivity extends AppCompatActivity {

    private Tugas tugasDipilih;
    private DataGlobal dataGlobal = DataGlobal.getInstance();

    TextInputEditText edtNamaTugas, edtTanggalTugas, edtJamTugas;
    Button btnSaveTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tugas_view);
        tugasDipilih = dataGlobal.tugasDipilih;
        inisialisasiView();
        inisialisasiListener();
        edtNamaTugas.setText(tugasDipilih.getNamaTugas());
        edtTanggalTugas.setText(tugasDipilih.getTanggalTugas() != null ? tugasDipilih.getTanggalTugas() : "");
        edtJamTugas.setText(tugasDipilih.getJamTugas() != null ? tugasDipilih.getJamTugas() : "");

    }

    @SuppressLint("ClickableViewAccessibility")
    private void inisialisasiListener() {
        edtTanggalTugas.setInputType(InputType.TYPE_NULL);
        edtJamTugas.setInputType(InputType.TYPE_NULL);
        edtTanggalTugas.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showDateDialog();
            }
            return false;
        });
        edtJamTugas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showTimeDialog();
                }
                return false;
            }
        });
        btnSaveTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rekamDataTugas();
                try {
                    setAlarmForNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    private void setAlarmForNotification() throws Exception {
        String tglWaktuTugas = tugasDipilih.getTanggalTugas() + " " + tugasDipilih.getJamTugas();
        SimpleDateFormat formatDateTugas = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        Date dateTugas = formatDateTugas.parse(tglWaktuTugas);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTugas);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, TugasBroadcastReceiver.class);
        intent.putExtra("nama_tugas", tugasDipilih.getNamaTugas());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void rekamDataTugas() {
        tugasDipilih.setNamaTugas(edtNamaTugas.getText().toString());
        tugasDipilih.setTanggalTugas(edtTanggalTugas.getText().toString());
        tugasDipilih.setJamTugas(edtJamTugas.getText().toString());
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                edtJamTugas.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void showDateDialog() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                edtTanggalTugas.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void inisialisasiView() {
        edtNamaTugas = findViewById(R.id.edtNamaTugas);
        edtTanggalTugas = findViewById(R.id.edtTanggal);
        edtJamTugas = findViewById(R.id.edtJam);
        btnSaveTugas = findViewById(R.id.btnSaveTugas);
    }

}
