package tech.hidetora.authserver.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;
import java.util.UUID;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Configuration
@RequiredArgsConstructor
public class ClientRunner {
    private final RegisteredClientRepository repository;


    @Bean
    ApplicationRunner runClients() {
        return args -> {
            var clientId = "spring";
            if (this.repository.findByClientId(clientId) == null) {
                this.repository.save(
                        RegisteredClient.withId(UUID.randomUUID().toString())
                                .clientId(clientId)
                                .clientSecret("{bcrypt}$2a$14$GvMY/gUdZ9cd7MbcsI2jQesAMj2tx8D1wV7YdBhOrik2Bfkf6MwMa")
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(Set.of(
                                        AuthorizationGrantType.AUTHORIZATION_CODE,
                                        AuthorizationGrantType.CLIENT_CREDENTIALS,
                                        AuthorizationGrantType.REFRESH_TOKEN
                                )))
                                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/spring")
                                .scopes(scopes -> scopes.addAll(Set.of(
                                        "user.write",
                                        "user.read",
                                        OidcScopes.OPENID
                                )))
                                .clientSettings(ClientSettings.builder()
                                        .requireAuthorizationConsent(true)
                                        .build())
                        .build()
                );

            }
        };
    }
}
