package com.example.pryandroidclinicaodontologo.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pryandroidclinicaodontologo.R;
import com.example.pryandroidclinicaodontologo.adapter.CitasAdapter;
import com.example.pryandroidclinicaodontologo.adapter.NotificacionAdapter;
import com.example.pryandroidclinicaodontologo.databinding.FragmentNotificacionesBinding;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;
import com.example.pryandroidclinicaodontologo.response.NotificacionesResponse;
import com.example.pryandroidclinicaodontologo.response.NotificacionesResponse;
import com.example.pryandroidclinicaodontologo.response.NotificacionesResponse;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesFragment extends Fragment {

    private static final String TAG = "AgendaCitaFragment";
    private FragmentNotificacionesBinding binding;
    private int citaId;
    private RecyclerView recyclerViewNotificaciones;
    private NotificacionAdapter NotificacionAdapter;

    private List<NotificacionesResponse.Data> notificacionesList;

    private static final String ARG_CITA_ID = "cita_id";
    public NotificacionesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            citaId = getArguments().getInt(ARG_CITA_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inflating fragment layout");
        View view = inflater.inflate(R.layout.fragment_detalle_historial_paciente, container, false);
        recyclerViewNotificaciones = view.findViewById(R.id.recyclerViewTratamientos);
        recyclerViewNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        
        cargarNotis(citaId);

        return view;
    }

    private void cargarNotis(int citaId) {
        // Implementa la lógica para cargar el historial médico del paciente usando el citaId
        Log.d(TAG, "Cargando notificaciones para la cita ID: " + citaId);

        // Llamada a la API para obtener el historial médico del paciente usando el cita_id
        RetrofitClient.createService().getNotificaciones(citaId).enqueue(new Callback<NotificacionesResponse>() {
            @Override
            public void onResponse(Call<NotificacionesResponse> call, Response<NotificacionesResponse> response) {
                if (response.isSuccessful()) {
                    NotificacionesResponse notificacionesResponse = response.body();
                    if (notificacionesResponse != null) {
                        Log.d(TAG, "onResponse: API call successful, status = " + notificacionesResponse.isStatus());
                        if (notificacionesResponse.isStatus()) {
                            notificacionesList = (List<NotificacionesResponse.Data>) notificacionesResponse.getData();
                            Log.d(TAG, "onResponse: notificacionesList size = " + notificacionesList.size());
                            NotificacionAdapter = new NotificacionAdapter(requireContext(), notificacionesList);

                            // Utiliza un Handler para asignar el adaptador con un pequeño retraso
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Log.d(TAG, "Handler: setting adapter to RecyclerView");
                                binding.recyclerViewNotificaciones.setAdapter(NotificacionAdapter);
                            });
                        } else {
                            Log.d(TAG, "onResponse: API call returned false, message = " + notificacionesResponse.getMessage());
                            Toast.makeText(getContext(), "Error: " + notificacionesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "onResponse: API response body is null");
                        Toast.makeText(getContext(), "Error: response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onResponse: API call failed, response code = " + response.code());
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificacionesResponse> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada a la API: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    
}