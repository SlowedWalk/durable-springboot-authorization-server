package tech.hidetora.authserver.keys;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import tech.hidetora.authserver.entity.RSAKeyPair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
@Repository
public class JdbcRsaKeyPairRepository implements RsaKeyPairRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RsaPublicKeyConverter rsaPublicKeyConverter;
    private final RsaPrivateKeyConverter rsaPrivateKeyConverter;
    private final RowMapper<RSAKeyPair> rsaKeyPairRowMapper;

    public JdbcRsaKeyPairRepository(
            JdbcTemplate jdbcTemplate,
            RsaPublicKeyConverter rsaPublicKeyConverter,
            RsaPrivateKeyConverter rsaPrivateKeyConverter,
            RowMapper<RSAKeyPair> keyPairRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rsaPublicKeyConverter = rsaPublicKeyConverter;
        this.rsaPrivateKeyConverter = rsaPrivateKeyConverter;
        this.rsaKeyPairRowMapper = keyPairRowMapper;
    }

    @Override
    public List<RSAKeyPair> findKeyPairs() {
        return this.jdbcTemplate.query(
                "select * from rsa_key_pairs order by created_at desc",
                this.rsaKeyPairRowMapper
        );
    }

    @Override
    public void save(RSAKeyPair rsaKeyPair) {
        var sql = """
                insert into rsa_key_pairs (id, private_key, public_key, created_at) values (?, ?, ?, ?)
                """;
        try(var privateBaos = new ByteArrayOutputStream(); var publicBaos = new ByteArrayOutputStream()) {
                    this.rsaPrivateKeyConverter.serialize(rsaKeyPair.privateKey(), privateBaos);
                    this.rsaPublicKeyConverter.serialize(rsaKeyPair.publicKey(), publicBaos);
                    var updated = this.jdbcTemplate.update(
                            sql,
                            rsaKeyPair.id(),
                            privateBaos.toString(),
                            publicBaos.toString(),
                            new Date(rsaKeyPair.createdAt().toEpochMilli())
                    );
                    Assert.state(updated == 0 || updated == 1, "no more than one record should be updated");
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to save RSA key pair", e);
                }
    }
}
