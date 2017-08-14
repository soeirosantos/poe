package br.com.soeirosantos.poe.cipher.service.impl;

import br.com.soeirosantos.poe.cipher.service.CipherService;
import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import br.com.soeirosantos.poe.core.exception.CipherException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class AesCipherService implements CipherService {

    private static final String PADDING = "AES/CBC/PKCS5PADDING";
    private static final String ALGORITHM = "AES";
    private final String KEY_IV;

    AesCipherService(ApplicationProperties properties) {
        KEY_IV = properties.getCryptContent().getAesKeyIv();
    }

    @Override
    public String encrypt(String message, byte[] key) {
        Objects.requireNonNull(message, "Message cannot be null while encrypting");
        String encryptedMessage;
        try {
            IvParameterSpec iv = new IvParameterSpec(KEY_IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            encryptedMessage = Base64.encodeBase64String(encrypted);
        } catch (GeneralSecurityException e) {
            String errorMessage = "Error encrypting message";
            log.error(errorMessage, e);
            throw new CipherException(errorMessage, e);
        }
        return encryptedMessage;
    }

    @Override
    public String decrypt(String encryptedMessage, byte[] key) {
        Objects.requireNonNull(encryptedMessage, "Message cannot be null while decrypting");
        String decryptedMessage;
        try {
            IvParameterSpec iv = new IvParameterSpec(KEY_IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] source = cipher.doFinal(Base64.decodeBase64(encryptedMessage));
            decryptedMessage = new String(source);
        } catch (GeneralSecurityException e) {
            String errorMessage = "Error decrypting message";
            log.error(errorMessage, e);
            throw new CipherException(errorMessage, e);
        }
        return decryptedMessage;
    }

}
