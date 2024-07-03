package com.example.pryandroidclinicaodontologo.response;

import java.util.List;

public class NotificacionesResponse {

    private boolean status;
    private List<NotificacionesResponse.Data> data;
    private String message;

    // Getters y Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NotificacionesResponse.Data> getData() {
        return data;
    }

    public void setData(List<NotificacionesResponse.Data> data) {
        this.data = data;
    }

    public static class Data {
        private String fecha;
        private int leida;
        private String mensaje;

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public int getLeida() {
            return leida;
        }

        public void setLeida(int leida) {
            this.leida = leida;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
