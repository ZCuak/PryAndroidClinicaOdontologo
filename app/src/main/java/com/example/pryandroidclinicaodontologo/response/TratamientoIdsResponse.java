package com.example.pryandroidclinicaodontologo.response;

import java.util.Map;

public class TratamientoIdsResponse {

    private boolean status;
    private Map<String, Integer> data;
    private String message;

    // Getters y Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
