package com.example.pryandroidclinicaodontologo.response;

import java.util.List;

public class OdontologosResponse {
    private boolean status;
    private String message;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OdontologosResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        private int id;
        private String nombre_completo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre_completo() {
            return nombre_completo;
        }

        public void setNombre_completo(String nombre_completo) {
            this.nombre_completo = nombre_completo;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", nombre_completo='" + nombre_completo + '\'' +
                    '}';
        }
    }
}
