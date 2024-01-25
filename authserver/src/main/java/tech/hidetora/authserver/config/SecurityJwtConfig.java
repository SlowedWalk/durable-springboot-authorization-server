package tech.hidetora.authserver.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import tech.hidetora.authserver.entity.RSAKeyPair;
import tech.hidetora.authserver.keys.RsaKeyPairRepository;

import java.util.List;

import static tech.hidetora.authserver.constants.SecurityConstant.AUTHORITIES_KEY;

@Configuration
@RequiredArgsConstructor
public class SecurityJwtConfig {
    private final RsaKeyPairRepository rsaKeyPairRepository;
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk=new RSAKey.Builder(getKeyPair().publicKey()).privateKey(getKeyPair().privateKey()).build();
        JWKSource<SecurityContext> jwkSource=new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(getKeyPair().publicKey()).build();
    }

    private RSAKeyPair getKeyPair() {
        List<RSAKeyPair> keyPairs = rsaKeyPairRepository.findKeyPairs();
        return keyPairs.size() != 0 ?  keyPairs.get(keyPairs.size() - 1) : null;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
