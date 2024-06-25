package com.example.pryandroidclinicaodontologo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pryandroidclinicaodontologo.R;

import com.example.pryandroidclinicaodontologo.databinding.CardviewCitaProgramadaBinding;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;

import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitaViewHolder> {

    public interface CitasAdapterListener {
        void onVerDetalle(CitasResponse.Data cita);
        void onReprogramarCita(CitasResponse.Data cita);
        void onCancelarCita(CitasResponse.Data cita);
    }

    private final Context context;
    private final List<CitasResponse.Data> citasList;
    private final CitasAdapterListener listener;

    public CitasAdapter(Context context, List<CitasResponse.Data> citasList, CitasAdapterListener listener) {
        this.context = context;
        this.citasList = citasList;
        this.listener = listener;
    }

    public static class CitaViewHolder extends RecyclerView.ViewHolder {
        private final CardviewCitaProgramadaBinding binding;

        public CitaViewHolder(CardviewCitaProgramadaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardviewCitaProgramadaBinding binding = CardviewCitaProgramadaBinding.inflate(inflater, parent, false);
        return new CitaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        CitasResponse.Data cita = citasList.get(position);
        holder.binding.tvFechaValue.setText(cita.getFecha());
        holder.binding.tvHoraValue.setText(cita.getHora());
        holder.binding.tvNombreOdontologoValue.setText(cita.getNombre_odontologo());
        holder.binding.tvMotivoConsulta.setText(cita.getMotivo_consulta());

        holder.binding.getRoot().setOnClickListener(v -> listener.onVerDetalle(cita));

        holder.binding.btnOpciones.setOnClickListener(v -> showPopupMenu(holder.binding.btnOpciones, cita));
    }

    private void showPopupMenu(View view, CitasResponse.Data cita) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_cita_opciones, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_reprogramar) {
                    listener.onReprogramarCita(cita);
                    return true;
                } else if (itemId == R.id.action_cancelar) {
                    listener.onCancelarCita(cita);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return citasList.size();
    }
}
