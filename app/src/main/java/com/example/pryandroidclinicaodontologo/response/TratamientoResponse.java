package com.example.pryandroidclinicaodontologo.response;

import java.util.List;

public class TratamientoResponse {
    private boolean status;
    private String message;
    private List<Tratamiento> data;

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

    public List<Tratamiento> getData() {
        return data;
    }

    public void setData(List<Tratamiento> data) {
        this.data = data;
    }

    public static class Tratamiento {
        private int id;
        private String nombre;
        private String descripcion;
        private double costo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public double getCosto() {
            return costo;
        }

        public void setCosto(double costo) {
            this.costo = costo;
        }
    }
}
