package tech.hidetora.authserver.service;

import tech.hidetora.authserver.dto.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}
