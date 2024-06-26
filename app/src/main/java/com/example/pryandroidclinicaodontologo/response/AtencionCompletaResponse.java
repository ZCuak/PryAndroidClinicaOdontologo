package com.example.pryandroidclinicaodontologo.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AtencionCompletaResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("cita_id")
        private int citaId;

        @SerializedName("diagnostico")
        private String diagnostico;

        @SerializedName("anotacion")
        private String anotacion;

        @SerializedName("tratamientos")
        private List<Tratamiento> tratamientos;

        @SerializedName("recetas")
        private List<Receta> recetas;

        public int getCitaId() {
            return citaId;
        }

        public String getDiagnostico() {
            return diagnostico;
        }

        public String getAnotacion() {
            return anotacion;
        }

        public List<Tratamiento> getTratamientos() {
            return tratamientos;
        }

        public List<Receta> getRecetas() {
            return recetas;
        }

        public static class Tratamiento {
            @SerializedName("id")
            private int id;

            @SerializedName("nombre")
            private String nombre;

            @SerializedName("descripcion")
            private String descripcion;

            @SerializedName("costo")
            private double costo;

            public int getId() {
                return id;
            }

            public String getNombre() {
                return nombre;
            }

            public String getDescripcion() {
                return descripcion;
            }

            public double getCosto() {
                return costo;
            }
        }

        public static class Receta {
            @SerializedName("id")
            private int id;

            @SerializedName("medicamento")
            private String medicamento;

            @SerializedName("dosis")
            private String dosis;

            public int getId() {
                return id;
            }

            public String getMedicamento() {
                return medicamento;
            }

            public String getDosis() {
                return dosis;
            }
        }
    }
}
