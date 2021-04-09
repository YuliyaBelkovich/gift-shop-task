package com.epam.esm.controller;

public class GiftShopErrorResponse {

    private int code;
    private String message;

    public GiftShopErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
