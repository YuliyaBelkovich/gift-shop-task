package com.epam.esm.security;

import com.epam.esm.controller.GiftShopErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ArrayList<String> errors = new ArrayList<>();
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        errors.add("This action requires authorization");
        GiftShopErrorResponse errorResponse = new GiftShopErrorResponse(40101, errors);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorResponse);
        out.flush();
    }

}
