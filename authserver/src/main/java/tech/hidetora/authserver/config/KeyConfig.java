package tech.hidetora.authserver.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.*;
import tech.hidetora.authserver.keys.Keys;
import tech.hidetora.authserver.keys.RsaKeyPairGenerationRequestEvent;
import tech.hidetora.authserver.keys.RsaKeyPairRepository;

import java.time.Instant;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Configuration
public class KeyConfig {

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationReadyListener(
            ApplicationEventPublisher publisher,
            RsaKeyPairRepository repository
    ) {
        return event -> {
            if (repository.findKeyPairs().isEmpty()) {
                publisher.publishEvent(
                        new RsaKeyPairGenerationRequestEvent(Instant.now()));
            }
        };
    }

    @Bean
    ApplicationListener<RsaKeyPairGenerationRequestEvent> rsaKeyPairGenerationRequestListener(
            Keys keys, RsaKeyPairRepository repository, @Value("${jwt.key.id}") String keyId) {
        return event -> repository.save(keys.generateKeyPair(keyId, event.getSource()));
    }

    @Bean
    TextEncryptor textEncryptor(
            @Value("${jwk.persistence.password}") String password,
            @Value("${jwk.persistence.salt}") String salt
    ) {
        return Encryptors.text(password, salt);
    }

//    @Bean
    NimbusJwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    OAuth2TokenGenerator<OAuth2Token> delegatingOAuth2TokenGenerator(
            JwtEncoder encoder,
            OAuth2TokenCustomizer<JwtEncodingContext> customizer) {
        var generator = new JwtGenerator(encoder);
        generator.setJwtCustomizer(customizer);
        return new DelegatingOAuth2TokenGenerator(generator,
                new OAuth2AccessTokenGenerator(), new OAuth2RefreshTokenGenerator());
    }
}
