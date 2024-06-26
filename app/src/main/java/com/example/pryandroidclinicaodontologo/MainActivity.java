package com.example.pryandroidclinicaodontologo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pryandroidclinicaodontologo.retrofit.ApiService;
import com.example.pryandroidclinicaodontologo.retrofit.RetrofitClient;
import com.example.pryandroidclinicaodontologo.response.LoginResponse;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtIngresarUsuario);
        password = findViewById(R.id.txtIngresarContraseña);
        loginButton = findViewById(R.id.btnIniciarSesion);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();

                // Verificar si los campos están vacíos
                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Campos de usuario o contraseña vacíos");
                    return;
                }

                // Convertir la contraseña a MD5
                String md5Password = md5(inputPassword);

                // Llamar a la API de login
                loginUser(inputUsername, md5Password);
            }
        });
    }

    private String md5(String s) {
        try {
            // Crear un MessageDigest para el algoritmo MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Crear una cadena hexadecimal a partir del hash
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void loginUser(String email, String clave) {
        ApiService apiService = RetrofitClient.createService();
        Call<LoginResponse> call = apiService.login(email, clave);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    int rolId = loginResponse.getData().getRol_id(); // Obtener el rol_id
                    Log.d(TAG, "rol_id: " + rolId);
                    if (loginResponse.isStatus() && loginResponse.getData().getRol_id() == 3) {

                        String token = loginResponse.getData().getToken();
                        int odontologoId = loginResponse.getData().getId(); // Supongamos que el ID del odontólogo es el ID del usuario
                        RetrofitClient.API_TOKEN = token;

                        // Guardar el token y los datos del usuario en SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", loginResponse.getData().getNombre_usuario());
                        editor.putString("token", token);
                        editor.putInt("odontologo_id", odontologoId); // Guardar el ID del odontólogo
                        editor.putString("nombreUsuario", loginResponse.getData().getNombre());
                        editor.putString("ape_completo", loginResponse.getData().getApe_completo());
                        editor.putString("fecha_nac", loginResponse.getData().getFecha_nac());
                        editor.putString("documento", loginResponse.getData().getDocumento());
                        editor.putString("email", loginResponse.getData().getEmail());
                        editor.putString("foto", loginResponse.getData().getFoto());
                        editor.putString("direccion", loginResponse.getData().getDireccion());
                        editor.putString("telefono", loginResponse.getData().getTelefono());
                        editor.apply();

                        // Redirigir a MenuActivity
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMessage = loginResponse.getMessage();
                        if (loginResponse.getData().getRol_id() != 3) {
                            errorMessage = "Acceso denegado. Usuario no autorizado.";
                        }
                        Toast.makeText(MainActivity.this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Login failed: " + errorMessage);
                    }
                } else {
                    try {
                        // Leer el cuerpo de la respuesta de error
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            JsonObject errorJson = new Gson().fromJson(errorBody, JsonObject.class);
                            String errorMessage = errorJson.has("message") ? errorJson.get("message").getAsString() : "Error desconocido";
                            Toast.makeText(MainActivity.this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Login failed: " + errorMessage);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Login failed: " + response.message());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login Failed: Error parsing error response", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Login failed: Error parsing error response", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Login Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Login failed: " + t.getMessage());
            }
        });
    }



}