package br.com.soeirosantos.poe.content.service;

import br.com.soeirosantos.poe.cipher.service.CipherService;
import br.com.soeirosantos.poe.cipher.service.SecretKeyService;
import br.com.soeirosantos.poe.content.exception.EncryptContentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class ContentListener {

    private final CipherService cipherService;
    private final SecretKeyService secretKeyService;

    ContentListener(CipherService cipherService, SecretKeyService secretKeyService) {
        this.cipherService = cipherService;
        this.secretKeyService = secretKeyService;
    }

    @EventListener(condition = "#event.content.encrypted")
    public void handleEncryptContent(EncryptContentEvent event) {
        if (StringUtils.isBlank(event.getToken())) {
            throw new EncryptContentException("Token cannot be null while encrypting content");
        }
        byte[] key = secretKeyService.getSecretKey(event.getToken());
        String encrypt = cipherService
            .encrypt(event.getContent().getBody(), key);
        event.getContent().setBody(encrypt);
        log.info("Content encrypted");
    }

    @EventListener(condition = "#event.content.encrypted")
    public void handleDecryptContent(DecryptContentEvent event) {
        if (StringUtils.isBlank(event.getToken())) {
            throw new EncryptContentException("Token cannot be null while decrypting content");
        }
        byte[] key = secretKeyService.getSecretKey(event.getToken());
        String decrypt = cipherService
            .decrypt(event.getContent().getBody(), key);
        event.getContent().setBody(decrypt);
        log.info("Content encrypted");
    }

}
