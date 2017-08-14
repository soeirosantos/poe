package br.com.soeirosantos.poe.cipher.service.impl;

import br.com.soeirosantos.poe.cipher.service.SecretKeyService;
import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class Sha1SecretKeyService implements SecretKeyService {

    private static final int _128_BIT_KEY = 16;
    private static final String ALGORITHM = "SHA-1";

    private final ApplicationProperties properties;

    Sha1SecretKeyService(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public byte[] getSecretKey(String rawKey) {
        Objects.requireNonNull(rawKey, "The key provided cannot be null");
        byte[] key = new byte[0];
        try {
            key = rawKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance(ALGORITHM);
            byte[] salt = properties.getCryptContent().getSalt().getBytes(StandardCharsets.UTF_8);
            key = sha.digest(ArrayUtils.addAll(salt, key));
            key = Arrays.copyOf(key, _128_BIT_KEY);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error handling secret key", e);
        }
        return key;
    }
}
