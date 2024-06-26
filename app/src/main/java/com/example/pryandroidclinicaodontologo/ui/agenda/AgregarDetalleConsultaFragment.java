package com.example.pryandroidclinicaodontologo.ui.agenda;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.pryandroidclinicaodontologo.databinding.ActivityAgregarDetalleConsultaFragmentBinding;
import com.example.pryandroidclinicaodontologo.response.DetalleCitaResponse;
import com.example.pryandroidclinicaodontologo.response.TratamientoResponse;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarDetalleConsultaFragment extends Fragment {

    private static final String TAG = "AgregarDetalleConsultaFragment";
    private ActivityAgregarDetalleConsultaFragmentBinding binding;
    private int citaId;
    private List<TratamientoResponse.Tratamiento> tratamientosList = new ArrayList<>();
    private boolean[] selectedTratamientos;
    private ArrayList<Integer> tratamientosSeleccionados = new ArrayList<>();
    private List<View> medicamentosViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityAgregarDetalleConsultaFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el cita_id del Bundle
        if (getArguments() != null) {
            citaId = getArguments().getInt("cita_id", -1);
        }

        if (citaId == -1) {
            Toast.makeText(getContext(), "ID de cita no válido", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID de cita no válido");
            return;
        }

        binding.txtDiagnostico.setMovementMethod(new ScrollingMovementMethod());
        binding.txtDiagnostico.setHorizontallyScrolling(false);

        // Llamar a la API para obtener los detalles de la cita
        RetrofitClient.createService().obtenerDetalleCita(citaId).enqueue(new Callback<DetalleCitaResponse>() {
            @Override
            public void onResponse(Call<DetalleCitaResponse> call, Response<DetalleCitaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DetalleCitaResponse cita = response.body();
                    if (cita.isStatus() && cita.getData() != null) {
                        DetalleCitaResponse.Data citaData = cita.getData();
                        binding.txtPaciente.setText(citaData.getNombre_paciente());
                        binding.txtMotivoConsulta.setText(citaData.getMotivo_consulta());
                        binding.txtDiagnostico.setText(citaData.getDiagnostico());
                        binding.txtTratamiento.setText(citaData.getAnotacion());
                    } else {
                        Toast.makeText(getContext(), cita.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener los detalles de la cita", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error al obtener los detalles de la cita: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DetalleCitaResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });

        // Llamar a la API para obtener la lista de tratamientos
        RetrofitClient.createService().getTratamientos().enqueue(new Callback<TratamientoResponse>() {
            @Override
            public void onResponse(Call<TratamientoResponse> call, Response<TratamientoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tratamientosList = response.body().getData();
                    selectedTratamientos = new boolean[tratamientosList.size()];
                } else {
                    Toast.makeText(getContext(), "Error al obtener la lista de tratamientos", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error al obtener la lista de tratamientos: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TratamientoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });

        // Configurar el campo de texto para seleccionar tratamientos
        binding.txtTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSeleccionTratamientos();
            }
        });


        // Configurar el botón para agregar medicamentos
        binding.btnAgregarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarMedicamento();
            }
        });

        // Configurar el botón para quitar medicamentos
        binding.btnQuitarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!medicamentosViews.isEmpty()) {
                    View lastMedicamentoView = medicamentosViews.remove(medicamentosViews.size() - 1);
                    binding.contenedorMedicamentos.removeView(lastMedicamentoView);
                } else {
                    Toast.makeText(getContext(), "No hay medicamentos para quitar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarDialogoSeleccionTratamientos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar Tratamientos");

        String[] tratamientosArray = new String[tratamientosList.size()];
        for (int i = 0; i < tratamientosList.size(); i++) {
            tratamientosArray[i] = tratamientosList.get(i).getNombre();
        }

        builder.setMultiChoiceItems(tratamientosArray, selectedTratamientos, (dialog, which, isChecked) -> {
            if (isChecked) {
                tratamientosSeleccionados.add(which);
            } else {
                tratamientosSeleccionados.remove(Integer.valueOf(which));
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            StringBuilder tratamientosSeleccionadosStr = new StringBuilder();
            for (int i = 0; i < tratamientosSeleccionados.size(); i++) {
                tratamientosSeleccionadosStr.append(tratamientosList.get(tratamientosSeleccionados.get(i)).getNombre());
                if (i != tratamientosSeleccionados.size() - 1) {
                    tratamientosSeleccionadosStr.append(", ");
                }
            }
            binding.txtTratamiento.setText(tratamientosSeleccionadosStr.toString());
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void mostrarDialogoAgregarMedicamento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Agregar Medicamento");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        final EditText inputNombre = new EditText(getContext());
        inputNombre.setHint("Nombre del Medicamento");
        layout.addView(inputNombre);

        final EditText inputDosis = new EditText(getContext());
        inputDosis.setHint("Dosis del Medicamento");
        layout.addView(inputDosis);

        builder.setView(layout);

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            String nombreMedicamento = inputNombre.getText().toString();
            String dosisMedicamento = inputDosis.getText().toString();

            if (!nombreMedicamento.isEmpty() && !dosisMedicamento.isEmpty()) {
                agregarMedicamentoView(nombreMedicamento, dosisMedicamento);
            } else {
                Toast.makeText(getContext(), "Debe ingresar el nombre y la dosis del medicamento", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void agregarMedicamentoView(String nombre, String dosis) {
        LinearLayout medicamentoLayout = new LinearLayout(getContext());
        medicamentoLayout.setOrientation(LinearLayout.VERTICAL);
        medicamentoLayout.setPadding(10, 10, 10, 10);

        EditText txtNombreMedicamento = new EditText(getContext());
        txtNombreMedicamento.setText(nombre);
        txtNombreMedicamento.setEnabled(false);
        txtNombreMedicamento.setFocusable(false);
        txtNombreMedicamento.setBackground(null);
        medicamentoLayout.addView(txtNombreMedicamento);

        EditText txtDosisMedicamento = new EditText(getContext());
        txtDosisMedicamento.setText(dosis);
        txtDosisMedicamento.setEnabled(false);
        txtDosisMedicamento.setFocusable(false);
        txtDosisMedicamento.setBackground(null);
        medicamentoLayout.addView(txtDosisMedicamento);

        medicamentosViews.add(medicamentoLayout);
        binding.contenedorMedicamentos.addView(medicamentoLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
