package br.com.soeirosantos.poe.core.services.impl;

import br.com.soeirosantos.poe.cipher.service.CipherService;
import br.com.soeirosantos.poe.cipher.service.SecretKeyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sha1SecretKeyServiceTest {

    private static final String SOME_RAW_KEY = "any text can be provided as a key";
    private static final String ANY_MESSAGE = "message";

    @Autowired
    private SecretKeyService secretKeyService;

    @Autowired
    private CipherService cipherService;

    @Test
    public void getSecretKey() throws Exception {
        byte[] secretKey = secretKeyService.getSecretKey(SOME_RAW_KEY);
        Assert.assertEquals(16, secretKey.length);
    }

    @Test
    public void secretKeyIsValidKey() {
        byte[] secretKey = secretKeyService.getSecretKey(SOME_RAW_KEY);
        String encrypt = cipherService.encrypt(ANY_MESSAGE, secretKey);
        Assert.assertNotNull(encrypt);
    }
}