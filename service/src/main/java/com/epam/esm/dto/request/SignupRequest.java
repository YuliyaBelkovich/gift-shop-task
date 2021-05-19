package com.epam.esm.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotNull(message = "Please, provide a name")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 symbols")
    private String username;

    @NotNull(message = "Please, provide an email")
    @Size(min = 5, max = 35, message = "Email length should be between 5 and 35 symbols")
    @Email
    private String email;

    @NotNull(message = "Please, provide a password")
    @Size(min = 6, max = 40, message = "Password length should be between 6 and 40 symbols")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
