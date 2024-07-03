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

public class TratamientoAdapter extends RecyclerView.Adapter<TratamientoAdapter.TratamientoViewHolder> {

    private List<HistorialResponse.Data.Tratamiento> tratamientoList;

    public TratamientoAdapter(List<HistorialResponse.Data.Tratamiento> tratamientoList) {
        this.tratamientoList = tratamientoList;
    }

    public void setTratamientos(List<HistorialResponse.Data.Tratamiento> tratamientos) {
        this.tratamientoList = tratamientos;
    }

    @NonNull
    @Override
    public TratamientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tratamiento, parent, false);
        return new TratamientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TratamientoViewHolder holder, int position) {
        HistorialResponse.Data.Tratamiento tratamiento = tratamientoList.get(position);
        holder.tratamiento.setText(tratamiento.getTratamiento());
        holder.descripcion.setText(tratamiento.getDescripcion_tratamiento());
        holder.costo.setText(String.valueOf(tratamiento.getCosto_tratamiento()));
        holder.fecha.setText(tratamiento.getFecha());
    }

    @Override
    public int getItemCount() {
        return tratamientoList.size();
    }

    public static class TratamientoViewHolder extends RecyclerView.ViewHolder {
        TextView tratamiento, descripcion, costo, fecha;

        public TratamientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tratamiento = itemView.findViewById(R.id.textViewTratamiento);
            descripcion = itemView.findViewById(R.id.textViewDescripcion);
            costo = itemView.findViewById(R.id.textViewCosto);
            fecha = itemView.findViewById(R.id.textViewFecha);
        }
    }
}


