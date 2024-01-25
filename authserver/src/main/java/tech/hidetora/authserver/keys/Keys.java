package tech.hidetora.authserver.keys;

import org.springframework.stereotype.Component;
import tech.hidetora.authserver.entity.RSAKeyPair;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
@Component
public class Keys {
    public RSAKeyPair generateKeyPair(String keyId, Instant createdAt) {
        KeyPair keyPair = generateKeyPair();
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKeyPair(keyId, privateKey, publicKey, createdAt);
    }

    private KeyPair generateKeyPair() {
     try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
     }
    }
}
