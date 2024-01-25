package tech.hidetora.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
@Configuration
public class SecurityConfig {

    @Bean
    JdbcOAuth2AuthorizationConsentService consentService(DataSource dataSource, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(new JdbcTemplate(dataSource), clientRepository);
    }

    @Bean
    JdbcOAuth2AuthorizationService authorizationService(DataSource dataSource, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationService(new JdbcTemplate(dataSource), clientRepository);
    }

    @Bean
    JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate template) {
        return new JdbcRegisteredClientRepository(template);
    }

//    @Bean
//    JWKSource<SecurityContext> jwkSource(
//            @Value("${jwt.key.id}") String id,
//            @Value("${jwt.key.private}") RSAPrivateKey privateKey,
//            @Value("${jwt.key.public}") RSAPublicKey publicKey) {
//        var rsa = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(id)
//                .build();
//        var jwk = new JWKSet(rsa);
//        return new ImmutableJWKSet<>(jwk);
//    }
}
