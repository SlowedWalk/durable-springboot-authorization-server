package tech.hidetora.authserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data=new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("errorMessage",accessDeniedException.getMessage());
        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}