package com.mobileprogramming.mercubuana.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileprogramming.mercubuana.todolist.model.Tugas;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.ViewHolder> {
    ArrayList<Tugas> listTugas;
    ItemClickListener itemClickListener;
    Context context;

    public TugasAdapter(ArrayList<Tugas> listTugas, Context context) {
        this.listTugas = listTugas;
        this.context = context;
    }

    @NonNull
    @Override
    public TugasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tugas_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TugasAdapter.ViewHolder holder, int position) {
        Tugas tugas = listTugas.get(position);
        holder.bind(position, tugas);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusAtauLengkapiData(holder, tugas);
            }
        });
    }

    private void hapusAtauLengkapiData(ViewHolder holder, Tugas tugas) {
        String infoHapusAtauLengkapi = String.format("Silahkan pilih untuk menghapus atau melengkapi data");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Hapus/Lengkapi Data");
        alertDialogBuilder.setMessage("Silahkan pilih untuk menghapus atau melengkapi data.");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_info);
        alertDialogBuilder.setMessage(infoHapusAtauLengkapi);
        alertDialogBuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemClickListener.hapusTugasListener(holder.getAdapterPosition(), tugas);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialogBuilder.setNeutralButton("Lengkapi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemClickListener.lengkapiDataTugasListener(holder.getAdapterPosition(), tugas);
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public int getItemCount() {
        return listTugas.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTugas;
        private final TextView tvJam;
        private final TextView tvTanggal;
        private final ImageView ivAlarm;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTugas = (TextView) itemView.findViewById(R.id.tvTugas);
            tvJam = (TextView) itemView.findViewById(R.id.tvJamTugas);
            tvTanggal = (TextView) itemView.findViewById(R.id.tvTanggalTugas);
            ivAlarm = (ImageView) itemView.findViewById(R.id.ivAlarm);
        }

        @SuppressLint("DefaultLocale")
        public void bind(int position, Tugas tugas) {
            tvTugas.setText(String.format("%d. %s", position + 1, tugas.getNamaTugas()));
            tvTanggal.setText(tugas.getTanggalTugas() != null ? tugas.getTanggalTugas() : "");
            tvJam.setText(tugas.getJamTugas() != null ? "/ " + tugas.getJamTugas() : "");
            if (tvJam.getText().toString().isEmpty() && tvTanggal.getText().toString().isEmpty()) {
                Glide.with(itemView)
                        .load(R.drawable.ic_alarm_off)
                        .into(ivAlarm);
            } else {
                Glide.with(itemView)
                        .load(R.drawable.ic_add_alarm)
                        .into(ivAlarm);
            }
        }
    }
}
