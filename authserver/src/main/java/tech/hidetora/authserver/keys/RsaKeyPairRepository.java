package tech.hidetora.authserver.keys;

import tech.hidetora.authserver.entity.RSAKeyPair;

import java.util.List;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public interface RsaKeyPairRepository {
    List<RSAKeyPair> findKeyPairs();

    void save(RSAKeyPair rsaKeyPair);
}
