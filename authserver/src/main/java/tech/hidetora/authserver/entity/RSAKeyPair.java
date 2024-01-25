package tech.hidetora.authserver.entity;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public record RSAKeyPair(
        String id,
        RSAPrivateKey privateKey,
        RSAPublicKey publicKey,
        Instant createdAt
) { }
