package tech.hidetora.authserver.keys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import tech.hidetora.authserver.entity.RSAKeyPair;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class RsaKeyPairRowMapper implements RowMapper<RSAKeyPair> {

    private final RsaPrivateKeyConverter rsaPrivateKeyConverter;
    private final RsaPublicKeyConverter rsaPublicKeyConverter;


    @Override
    public RSAKeyPair mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            var privateKeyBytes = rs.getString("private_key").getBytes();
            var publicKeyBytes = rs.getString("public_key").getBytes();

            var privateKey = this.rsaPrivateKeyConverter.deserializeFromByteArray(privateKeyBytes);
            var publicKey = this.rsaPublicKeyConverter.deserializeFromByteArray(publicKeyBytes);
            Date sqlDate = rs.getDate("created_at");
            Instant createdAt = sqlDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            var id = rs.getString("id");

            return new RSAKeyPair(id, privateKey, publicKey, createdAt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
