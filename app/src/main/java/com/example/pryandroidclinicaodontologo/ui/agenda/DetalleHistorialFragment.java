package com.example.pryandroidclinicaodontologo.ui.agenda;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pryandroidclinicaodontologo.R;
import com.example.pryandroidclinicaodontologo.adapter.TratamientoAdapter;
import com.example.pryandroidclinicaodontologo.adapter.RecetaAdapter;
import com.example.pryandroidclinicaodontologo.response.HistorialResponse;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleHistorialFragment extends Fragment {
    private static final String ARG_CITA_ID = "cita_id";
    private static final String TAG = "DetalleHistorialFragment";
    private int citaId;
    private RecyclerView recyclerViewTratamientos;
    private RecyclerView recyclerViewRecetas;
    private TratamientoAdapter tratamientoAdapter;
    private RecetaAdapter recetaAdapter;

    public DetalleHistorialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            citaId = getArguments().getInt(ARG_CITA_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_historial_paciente, container, false);

        recyclerViewTratamientos = view.findViewById(R.id.recyclerViewTratamientos);
        recyclerViewRecetas = view.findViewById(R.id.recyclerViewRecetas);

        recyclerViewTratamientos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRecetas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar adaptadores vacíos
        tratamientoAdapter = new TratamientoAdapter(new ArrayList<>());
        recetaAdapter = new RecetaAdapter(new ArrayList<>());

        recyclerViewTratamientos.setAdapter(tratamientoAdapter);
        recyclerViewRecetas.setAdapter(recetaAdapter);

        // Usa el citaId para cargar los detalles del historial médico del paciente
        cargarHistorialMedico(citaId);

        return view;
    }

    private void cargarHistorialMedico(int citaId) {
        // Implementa la lógica para cargar el historial médico del paciente usando el citaId
        Log.d(TAG, "Cargando historial médico para la cita ID: " + citaId);

        // Llamada a la API para obtener el historial médico del paciente usando el cita_id
        RetrofitClient.createService().getDetalleHistorialPorCita(citaId).enqueue(new Callback<HistorialResponse>() {
            @Override
            public void onResponse(Call<HistorialResponse> call, Response<HistorialResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HistorialResponse historialResponse = response.body();
                    if (historialResponse.isStatus()) {
                        // Mostrar el historial médico
                        HistorialResponse.Data data = historialResponse.getData();
                        Log.d(TAG, "Datos obtenidos: " + data.toString());
                        mostrarHistorial(data);
                    } else {
                        Log.e(TAG, "Error en respuesta: " + historialResponse.getMessage());
                        Toast.makeText(getContext(), "Error: " + historialResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Error en la llamada a la API: Código " + response.code() + ", Mensaje: " + response.message());
                    Toast.makeText(getContext(), "Error: Código " + response.code() + ", Mensaje: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistorialResponse> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada a la API: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarHistorial(HistorialResponse.Data data) {
        Log.d(TAG, "Mostrando historial médico");
        tratamientoAdapter.setTratamientos(data.getTratamientos());
        recetaAdapter.setRecetas(data.getRecetas());

        tratamientoAdapter.notifyDataSetChanged();
        recetaAdapter.notifyDataSetChanged();

        for (HistorialResponse.Data.Tratamiento tratamiento : data.getTratamientos()) {
            Log.d(TAG, "Tratamiento: " + tratamiento.getTratamiento());
            Log.d(TAG, "Descripción: " + tratamiento.getDescripcion_tratamiento());
            Log.d(TAG, "Costo: " + tratamiento.getCosto_tratamiento());
            Log.d(TAG, "Fecha: " + tratamiento.getFecha());
        }
        for (HistorialResponse.Data.Receta receta : data.getRecetas()) {
            Log.d(TAG, "Receta ID: " + receta.getReceta_id());
            Log.d(TAG, "Medicamento: " + receta.getMedicamento());
            Log.d(TAG, "Dosis: " + receta.getDosis());
            Log.d(TAG, "Fecha: " + receta.getFecha());
        }
    }
}
