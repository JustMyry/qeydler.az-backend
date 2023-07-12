package com.exnotis.backend.security.jwt;


import com.exnotis.backend.security.constants.SecurityConstants;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//(Access Denied) mesajini aliciya vermek ucundur.
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {



    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        epResponseStatus epResponseStatus = new epResponseStatus(HttpStatus.UNAUTHORIZED.value(), SecurityConstants.ACCESS_DENIED_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, epResponseStatus);
        outputStream.flush();
    }



}