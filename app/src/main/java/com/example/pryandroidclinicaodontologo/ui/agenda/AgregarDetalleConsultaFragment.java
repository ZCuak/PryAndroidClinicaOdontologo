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

import com.example.pryandroidclinicaodontologo.databinding.ActivityAgregarDetalleConsultaFragmentBinding;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarDetalleConsultaFragment extends Fragment {

    private static final String TAG = "AgregarDetalleConsultaFragment";
    private ActivityAgregarDetalleConsultaFragmentBinding binding;
    private int citaId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityAgregarDetalleConsultaFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            citaId = getArguments().getInt("cita_id", -1);
        }

        if (citaId == -1) {
            Toast.makeText(getContext(), "ID de cita no válido", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID de cita no válido");
            return;
        }

        // Llamar a la API para obtener los detalles de la cita
        RetrofitClient.createService().obtenerDetalleCita(citaId).enqueue(new Callback<CitasResponse>() {
            @Override
            public void onResponse(Call<CitasResponse> call, Response<CitasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CitasResponse cita = response.body();
                    if (cita.isStatus() && cita.getData() != null) {
                        CitasResponse.Data citaData = (CitasResponse.Data) cita.getData();
                        binding.txtPaciente.setText(citaData.getNombre_paciente());
                        binding.txtMotivoConsulta.setText(citaData.getMotivo_consulta());
                        binding.txtDiagnostico.setText(citaData.getDiagnostico());
                        binding.txtTratamiento.setText(citaData.getAnotacion());
                        binding.txtReceta.setText(citaData.getCosto().toString());
                    } else {
                        Toast.makeText(getContext(), cita.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener los detalles de la cita", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error al obtener los detalles de la cita: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CitasResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
