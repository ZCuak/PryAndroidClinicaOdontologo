package com.example.pryandroidclinicaodontologo.response;

public class DetalleCitaResponse {
    private boolean status;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private Integer cita_id;
        private String nombre_paciente;
        private String nombre_odontologo;
        private String fecha;
        private String hora;
        private String motivo_consulta;
        private String diagnostico;
        private String anotacion;
        private Double costo;
        private String estado;

        // Getters y Setters
        public Integer getCita_id() {
            return cita_id;
        }

        public void setCita_id(Integer cita_id) {
            this.cita_id = cita_id;
        }

        public String getNombre_paciente() {
            return nombre_paciente;
        }

        public void setNombre_paciente(String nombre_paciente) {
            this.nombre_paciente = nombre_paciente;
        }

        public String getNombre_odontologo() {
            return nombre_odontologo;
        }

        public void setNombre_odontologo(String nombre_odontologo) {
            this.nombre_odontologo = nombre_odontologo;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getMotivo_consulta() {
            return motivo_consulta;
        }

        public void setMotivo_consulta(String motivo_consulta) {
            this.motivo_consulta = motivo_consulta;
        }

        public String getDiagnostico() {
            return diagnostico;
        }

        public void setDiagnostico(String diagnostico) {
            this.diagnostico = diagnostico;
        }

        public String getAnotacion() {
            return anotacion;
        }

        public void setAnotacion(String anotacion) {
            this.anotacion = anotacion;
        }

        public Double getCosto() {
            return costo;
        }

        public void setCosto(Double costo) {
            this.costo = costo;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
