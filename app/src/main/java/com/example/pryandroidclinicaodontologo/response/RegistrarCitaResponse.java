package com.example.pryandroidclinicaodontologo.response;

public class RegistrarCitaResponse {
    private boolean status;
    private String message;
    private Data data;

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

    @Override
    public String toString() {
        return "RegistrarCitaResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        private int atencion_id;

        public int getAtencion_id() {
            return atencion_id;
        }

        public void setAtencion_id(int atencion_id) {
            this.atencion_id = atencion_id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "atencion_id=" + atencion_id +
                    '}';
        }
    }
}
