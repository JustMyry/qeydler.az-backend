package com.exnotis.backend.auth.entrypoint;

import com.exnotis.backend.security.constants.SecurityConstants;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
// 403 FORBIDDEN mesajini aliciya vermek ucundur.
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        epResponseStatus epResponseStatus = new epResponseStatus(HttpStatus.FORBIDDEN.value(), SecurityConstants.FORBIDDEN_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, epResponseStatus);
        outputStream.flush();
    }


}
