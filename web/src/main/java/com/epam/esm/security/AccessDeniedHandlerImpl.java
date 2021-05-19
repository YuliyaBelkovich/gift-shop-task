package com.epam.esm.security;

import com.epam.esm.controller.GiftShopErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ArrayList<String> errors = new ArrayList<>();
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        errors.add("Access denied");
        GiftShopErrorResponse errorResponse = new GiftShopErrorResponse(40301, errors);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorResponse);
        out.flush();
    }
}
