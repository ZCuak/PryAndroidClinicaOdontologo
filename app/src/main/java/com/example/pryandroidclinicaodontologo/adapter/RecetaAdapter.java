package com.example.pryandroidclinicaodontologo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pryandroidclinicaodontologo.R;
import com.example.pryandroidclinicaodontologo.response.HistorialResponse;

import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {

    private List<HistorialResponse.Data.Receta> recetaList;

    public RecetaAdapter(List<HistorialResponse.Data.Receta> recetaList) {
        this.recetaList = recetaList;
    }

    public void setRecetas(List<HistorialResponse.Data.Receta> recetas) {
        this.recetaList = recetas;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
        return new RecetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        HistorialResponse.Data.Receta receta = recetaList.get(position);
        holder.medicamento.setText(receta.getMedicamento());
        holder.dosis.setText(receta.getDosis());
        holder.fecha.setText(receta.getFecha());
    }

    @Override
    public int getItemCount() {
        return recetaList.size();
    }

    public static class RecetaViewHolder extends RecyclerView.ViewHolder {
        TextView medicamento, dosis, fecha;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);
            medicamento = itemView.findViewById(R.id.textViewMedicamento);
            dosis = itemView.findViewById(R.id.textViewDosis);
            fecha = itemView.findViewById(R.id.textViewFecha);
        }
    }
}



