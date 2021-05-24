package com.epam.esm.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotNull(message = "{login.name}")
    @Size(min = 2, max = 30, message = "{login.name.message}")
    private String username;

    @NotNull(message = "{login.email}")
    @Size(min = 5, max = 35, message = "{login.email.message}")
    @Email
    private String email;

    @NotNull(message = "{login.password}")
    @Size(min = 6, max = 40, message = "{login.password.message}")
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
