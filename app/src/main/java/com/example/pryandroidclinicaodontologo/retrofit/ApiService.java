package com.example.pryandroidclinicaodontologo.retrofit;

import com.example.pryandroidclinicaodontologo.response.LoginResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("clave") String clave);

    @FormUrlEncoded
    @PUT("/usuario/actualizar")
    Call<LoginResponse> actualizarUsuario(
            @Field("id") int id,
            @Field("nombreUsuario") String nombreUsuario,
            @Field("email") String email,
            @Field("estado") int estado,
            @Field("token") String token,
            @Field("estadoToken") int estadoToken,
            @Field("nombre") String nombre,
            @Field("apeCompleto") String apeCompleto,
            @Field("fechaNac") String fechaNacimiento,
            @Field("documento") String documento,
            @Field("tipo_documento_id") int tipo_documento_id,
            @Field("sexo") int sexo,
            @Field("direccion") String direccion,
            @Field("telefono") String telefono,
            @Field("foto") String foto,
            @Field("rolId") int rolId
    );

    @Multipart
    @POST("/usuario/subirFoto")
    Call<LoginResponse> subirFoto(@Part MultipartBody.Part foto);

    @GET("/img/{filename}")
    Call<LoginResponse> obtenerFoto(@Path("filename") String filename);
    @FormUrlEncoded
    @POST("/usuario/cambiarContrasena")
    Call<LoginResponse> cambiarContrasena(
            @Field("id") int id,
            @Field("nuevaContrasena") String nuevaContrasena
    );
}