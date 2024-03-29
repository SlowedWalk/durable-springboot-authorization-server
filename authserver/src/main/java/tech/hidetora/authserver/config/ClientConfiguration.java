package tech.hidetora.authserver.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Set;
import java.util.UUID;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Configuration
public class ClientConfiguration {

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate template) {
        return new JdbcRegisteredClientRepository(template);
    }

    @Bean
    ApplicationRunner clientsRunner(RegisteredClientRepository repository) {
        return args -> {
            var clientId = "crm";
            if (repository.findByClientId(clientId) == null) {
                repository.save(
                        RegisteredClient.withId(UUID.randomUUID().toString())
                                .clientId(clientId)
                                .clientSecret("{bcrypt}$2a$14$QjNlaWdhx/1EXzdr0d4lDe2L06FLvIL/Z.NMs1ERbJ/0xnHBGKm5a")
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
                                ))).build()
                );

            }
        };
    }
}
