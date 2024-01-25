package tech.hidetora.authserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.hidetora.authserver.dto.APIResponse;
import tech.hidetora.authserver.dto.request.LoginRequest;
import tech.hidetora.authserver.service.AuthService;

import java.time.Instant;

import static tech.hidetora.authserver.constants.SecurityConstant.API_V1_AUTH;
import static tech.hidetora.authserver.constants.SecurityConstant.LOGIN;

@RestController
@RequestMapping(API_V1_AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
//    private final UserService userService;

    @PostMapping(LOGIN)
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequest loginRequest) {
        String login = authService.login(loginRequest);
        return ResponseEntity.ok(APIResponse.builder()
                .status(HttpStatus.OK)
                .message("Login successful")
                .data(login)
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now().toString())
                .success(true)
                .build());
    }
}
