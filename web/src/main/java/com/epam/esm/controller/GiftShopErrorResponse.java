package com.epam.esm.controller;

import java.util.List;

public class GiftShopErrorResponse {

    private int code;
    private List<String> details;

    public GiftShopErrorResponse(int code, List<String> details) {
        this.code = code;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public List<String> getMessage() {
        return details;
    }
}
