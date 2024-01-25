package tech.hidetora.authserver.keys;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public class RsaPublicKeyConverter implements Serializer<RSAPublicKey>, Deserializer<RSAPublicKey> {

    private final TextEncryptor textEncryptor;

    public RsaPublicKeyConverter(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    public RSAPublicKey deserialize(InputStream inputStream) {
        try {
            var pem = textEncryptor.decrypt(FileCopyUtils.copyToString(new InputStreamReader(inputStream)));
            var publicKeyPEM = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            var encoded = Base64.getMimeDecoder().decode(publicKeyPEM);
            var keyFactory = KeyFactory.getInstance("RSA");
            var keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
         } catch (Throwable throwable) {
            throw new IllegalArgumentException("Failed to deserialize RSAPublicKey", throwable);
        }
    }

    @Override
    public void serialize(RSAPublicKey rsaPublicKey, OutputStream outputStream) throws IOException {
        var x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        var pem = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getMimeEncoder().encodeToString(x509EncodedKeySpec.getEncoded()) +
                "\n-----END PUBLIC KEY-----";
        outputStream.write(textEncryptor.encrypt(pem).getBytes());
    }
}
