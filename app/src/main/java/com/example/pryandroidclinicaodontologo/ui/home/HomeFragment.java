package com.example.pryandroidclinicaodontologo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.pryandroidclinicaodontologo.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.layoutAgendaCita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu);
                navController.navigate(R.id.action_nav_home_to_agendaCitaFragment);
            }
        });

        view.findViewById(R.id.layoutRegistroAtencion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu);
                navController.navigate(R.id.action_nav_home_to_registroAtencionFragment);
            }
        });

        view.findViewById(R.id.layoutHistorialMedico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu);
                navController.navigate(R.id.action_nav_home_to_historialMedicoFragment);
            }
        });

        return view;
    }
}
