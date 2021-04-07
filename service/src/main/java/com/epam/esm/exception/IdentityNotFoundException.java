package com.epam.esm.exception;

public class IdentityNotFoundException extends RuntimeException {

    private int id;

    public IdentityNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
