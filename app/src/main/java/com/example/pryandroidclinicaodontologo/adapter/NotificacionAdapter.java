package com.example.pryandroidclinicaodontologo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pryandroidclinicaodontologo.databinding.CardViewNotificacionesBinding;
import com.example.pryandroidclinicaodontologo.databinding.CardviewCitaProgramadaBinding;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;
import com.example.pryandroidclinicaodontologo.response.HistorialResponse;
import com.example.pryandroidclinicaodontologo.response.NotificacionesResponse;

import java.util.ArrayList;
import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotiViewHolder>  {

    private final Context context;
    private final List<NotificacionesResponse.Data> notisList;


    public NotificacionAdapter(Context context, List<NotificacionesResponse.Data> notisList) {
        this.context = context;
        this.notisList = notisList;
    }


    @NonNull
    @Override
    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardViewNotificacionesBinding binding = CardViewNotificacionesBinding.inflate(inflater, parent, false);
        return new NotificacionAdapter.NotiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiViewHolder holder, int position) {
        NotificacionesResponse.Data noti = notisList.get(position);
        holder.binding.txtFechaHora.setText(noti.getFecha());
        holder.binding.txtMensaje.setText(noti.getMensaje());
        holder.binding.txtEstado.setText(noti.getLeida());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NotiViewHolder extends RecyclerView.ViewHolder {
        private final CardViewNotificacionesBinding binding;

        public NotiViewHolder(CardViewNotificacionesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
