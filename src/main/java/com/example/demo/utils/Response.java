package com.example.demo.utils;

public class Response {
    public boolean success;
    public String message;
    public Object data;

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
