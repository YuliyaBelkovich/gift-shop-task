package com.epam.esm.dto.request;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Please, provide a name")
    private String username;

    @NotNull(message = "Please, provide password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}