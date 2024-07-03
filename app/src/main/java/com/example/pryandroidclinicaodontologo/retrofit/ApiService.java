package com.example.pryandroidclinicaodontologo.retrofit;

import com.example.pryandroidclinicaodontologo.response.AtencionCompletaResponse;
import com.example.pryandroidclinicaodontologo.response.CitasResponse;
import com.example.pryandroidclinicaodontologo.response.DetalleCitaResponse;
import com.example.pryandroidclinicaodontologo.response.HistorialResponse;
import com.example.pryandroidclinicaodontologo.response.LoginResponse;
import com.example.pryandroidclinicaodontologo.response.NotificacionesResponse;
import com.example.pryandroidclinicaodontologo.response.OdontologosResponse;
import com.example.pryandroidclinicaodontologo.response.RegistrarCitaResponse;
import com.example.pryandroidclinicaodontologo.response.TratamientoIdsResponse;
import com.example.pryandroidclinicaodontologo.response.TratamientoResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("/atencion/citas/")
    Call<CitasResponse> getCitasProgramadas();

    @GET("/atencion/odontologos")
    Call<OdontologosResponse> getOdontologos();

    @FormUrlEncoded
    @POST("/atencion/cita/registrar")
    Call<RegistrarCitaResponse> registrarCita(
            @Field("paciente_id") int pacienteId,
            @Field("odontologo_id") int odontologoId,
            @Field("fecha") String fecha,
            @Field("hora") String hora,
            @Field("motivo_consulta") String motivoConsulta
    );

    @FormUrlEncoded
    @POST("/atencion/cita/cancelar")
    Call<Void> cancelarCita(
            @Field("cita_id") int citaID
    );

    @GET("/atencion/citas-por-odontologo/{odontologo_id}")
    Call<CitasResponse> getCitasPorOdontologo(@Path("odontologo_id") int odontologoId);

    @GET("/atencion/detalle-cita/{cita_id}")
    Call<DetalleCitaResponse> obtenerDetalleCita(@Path("cita_id") int citaId);

    @GET("/tratamiento/lista")
    Call<TratamientoResponse> getTratamientos();


    @FormUrlEncoded
    @POST("/cita/registrar_completa")
    Call<AtencionCompletaResponse> registrarCompleta(
            @Field("id") int citaId,
            @Field("diagnostico") String diagnostico,
            @Field("anotacion") String anotacion,
            @Field("tratamientos") List<Integer> tratamientos,
            @Field("recetas") List<String> recetas  // Aquí enviamos la receta como un string JSON
    );


    @POST("/tratamiento/obtener_ids")
    Call<TratamientoIdsResponse> obtenerIdsTratamientos(@Body Map<String, List<String>> nombres);

    @GET("atencion/detalle-historial2/{cita_id}")
    Call<HistorialResponse> getDetalleHistorialPorCita(@Path("cita_id") int citaId);

    @GET("/notificacion/lista/{cita_id}")
    Call<NotificacionesResponse> getNotificaciones(@Path("cita_id") int citaId);

}
