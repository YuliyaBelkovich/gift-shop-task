package com.epam.esm.exception;

import com.epam.esm.models.Identifiable;

public class IdentityAlreadyExistsException extends RuntimeException {
    private String identity;

    public IdentityAlreadyExistsException(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
