package tech.hidetora.authserver.keys;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import tech.hidetora.authserver.entity.RSAKeyPair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
@Component
@RequiredArgsConstructor
public class RsaKeyPairRepositoryJWKSource implements JWKSource<SecurityContext>, OAuth2TokenCustomizer<JwtEncodingContext> {
    private final RsaKeyPairRepository keyPairRepository;

    /**
     * @param jwkSelector
     * @param securityContext
     * @return @link List<JWK> a list of JWKs that match the selector
     * @throws KeySourceException
     */
    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
        List<RSAKeyPair> keyPairs = this.keyPairRepository.findKeyPairs();
        List<JWK> result = new ArrayList<JWK>(keyPairs.size());
        for (RSAKeyPair keyPair : keyPairs) {

            RSAKey rsaKey = new RSAKey.Builder(keyPair.publicKey())
                    .privateKey(keyPair.privateKey())
                    .keyID(keyPair.id())
                    .build();
            if (jwkSelector.getMatcher().matches(rsaKey)) {
                result.add(rsaKey);
            }
        }
        return result;
    }

    /**
     * @param context
     */
    @Override
    public void customize(JwtEncodingContext context) {
        List<RSAKeyPair> keyPairs = this.keyPairRepository.findKeyPairs();
        String kid = keyPairs.get(0).id();
        context.getJwsHeader().keyId(kid);
    }
}