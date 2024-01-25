package tech.hidetora.authserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record APIResponse(
        String message,
        Object data,
        boolean success,
        HttpStatus status,
        String error,
        int statusCode,
        String timestamp
) { }
