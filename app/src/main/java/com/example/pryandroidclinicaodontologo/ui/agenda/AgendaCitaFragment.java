package com.example.pryandroidclinicaodontologo.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pryandroidclinicaodontologo.R;
import com.example.pryandroidclinicaodontologo.adapter.CitasAdapter;
import com.example.pryandroidclinicaodontologo.databinding.FragmentAgendaCitaBinding;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaCitaFragment extends Fragment implements CitasAdapter.CitasAdapterListener {

    private static final String TAG = "AgendaCitaFragment";
    private FragmentAgendaCitaBinding binding;
    private CitasAdapter citasAdapter;
    private List<CitasResponse.Data> citasList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inflating fragment layout");
        binding = FragmentAgendaCitaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: initializing RecyclerView");

        // Inicializa el RecyclerView y asigna el LayoutManager
        binding.recyclerViewCitas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener el ID del odontólogo desde SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        int odontologoId = sharedPreferences.getInt("odontologo_id", -1);

        // Imprimir el ID del odontólogo
        Log.d(TAG, "ID del odontólogo: " + odontologoId);

        // Verificar que el ID del odontólogo sea válido
        if (odontologoId == -1) {
            Toast.makeText(getContext(), "Error: ID de odontólogo no válido", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID de odontólogo no válido");
            return;
        }

        // Llamar a la API para obtener las citas filtradas por odontólogo
        Log.d(TAG, "onViewCreated: making API call to getCitasPorOdontologo");
        RetrofitClient.createService().getCitasPorOdontologo(odontologoId).enqueue(new Callback<CitasResponse>() {
            @Override
            public void onResponse(Call<CitasResponse> call, Response<CitasResponse> response) {
                if (response.isSuccessful()) {
                    CitasResponse citasResponse = response.body();
                    if (citasResponse != null) {
                        Log.d(TAG, "onResponse: API call successful, status = " + citasResponse.isStatus());
                        if (citasResponse.isStatus()) {
                            citasList = citasResponse.getData();
                            Log.d(TAG, "onResponse: citasList size = " + citasList.size());
                            citasAdapter = new CitasAdapter(requireContext(), citasList, AgendaCitaFragment.this);

                            // Utiliza un Handler para asignar el adaptador con un pequeño retraso
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Log.d(TAG, "Handler: setting adapter to RecyclerView");
                                binding.recyclerViewCitas.setAdapter(citasAdapter);
                            });
                        } else {
                            Log.d(TAG, "onResponse: API call returned false, message = " + citasResponse.getMessage());
                            Toast.makeText(getContext(), "Error: " + citasResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<CitasResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: API call failed, error = " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onVerDetalle(CitasResponse.Data cita) {
        Log.d(TAG, "onVerDetalle: viewing details for citaId = " + cita.getCita_id());
        // Crear un bundle y agregar el cita_id
        Bundle bundle = new Bundle();
        bundle.putInt("cita_id", cita.getCita_id());
        NavHostFragment.findNavController(AgendaCitaFragment.this)
                .navigate(R.id.action_nav_home_to_notificacionFragment, bundle);

    }

    @Override
    public void onRegistrarAtencion(CitasResponse.Data cita) {
        Log.d(TAG, "onRegistrarAtencion: reprogramming citaId = " + cita.getCita_id());
        Toast.makeText(getContext(), "Reprogramar la cita con ID: " + cita.getCita_id(), Toast.LENGTH_SHORT).show();

        // Crear un bundle y agregar el cita_id
        Bundle bundle = new Bundle();
        bundle.putInt("cita_id", cita.getCita_id());

        // Navegar al fragmento AgregarDetalleConsultaFragment pasando el bundle
        NavHostFragment.findNavController(AgendaCitaFragment.this)
                .navigate(R.id.action_agendaCitaFragment_to_agregarDetalleConsultaFragment, bundle);
    }

    @Override
    public void onCancelarCita(CitasResponse.Data cita) {
        Log.d(TAG, "onCancelarCita: viewing medical history for citaId = " + cita.getCita_id());
        Toast.makeText(getContext(), "Ver historial médico del paciente con ID de cita: " + cita.getCita_id(), Toast.LENGTH_SHORT).show();

        // Crear un bundle y agregar el cita_id
        Bundle bundle = new Bundle();
        bundle.putInt("cita_id", cita.getCita_id());

        // Navegar al fragmento DetalleHistorialFragment pasando el bundle
        NavHostFragment.findNavController(AgendaCitaFragment.this)
                .navigate(R.id.action_agendaCitaFragment_to_detalleHistorialFragment, bundle);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: cleaning up");
        binding = null;
    }
}
