package com.example.pryandroidclinicaodontologo.response;

import java.util.List;

public class HistorialResponse {
    private boolean status;
    private Data data;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data {
        private List<Receta> recetas;
        private List<Tratamiento> tratamientos;

        public List<Receta> getRecetas() {
            return recetas;
        }

        public void setRecetas(List<Receta> recetas) {
            this.recetas = recetas;
        }

        public List<Tratamiento> getTratamientos() {
            return tratamientos;
        }

        public void setTratamientos(List<Tratamiento> tratamientos) {
            this.tratamientos = tratamientos;
        }

        public static class Receta {
            private int receta_id;
            private String medicamento;
            private String dosis;
            private String fecha;

            // Getters y setters
            public int getReceta_id() {
                return receta_id;
            }

            public void setReceta_id(int receta_id) {
                this.receta_id = receta_id;
            }

            public String getMedicamento() {
                return medicamento;
            }

            public void setMedicamento(String medicamento) {
                this.medicamento = medicamento;
            }

            public String getDosis() {
                return dosis;
            }

            public void setDosis(String dosis) {
                this.dosis = dosis;
            }

            public String getFecha() {
                return fecha;
            }

            public void setFecha(String fecha) {
                this.fecha = fecha;
            }
        }

        public static class Tratamiento {
            private String tratamiento;
            private String descripcion_tratamiento;
            private double costo_tratamiento;
            private String fecha;

            // Getters y setters
            public String getTratamiento() {
                return tratamiento;
            }

            public void setTratamiento(String tratamiento) {
                this.tratamiento = tratamiento;
            }

            public String getDescripcion_tratamiento() {
                return descripcion_tratamiento;
            }

            public void setDescripcion_tratamiento(String descripcion_tratamiento) {
                this.descripcion_tratamiento = descripcion_tratamiento;
            }

            public double getCosto_tratamiento() {
                return costo_tratamiento;
            }

            public void setCosto_tratamiento(double costo_tratamiento) {
                this.costo_tratamiento = costo_tratamiento;
            }

            public String getFecha() {
                return fecha;
            }

            public void setFecha(String fecha) {
                this.fecha = fecha;
            }
        }
    }
}


