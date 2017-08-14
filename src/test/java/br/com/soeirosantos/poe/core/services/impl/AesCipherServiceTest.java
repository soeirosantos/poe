package br.com.soeirosantos.poe.core.services.impl;

import br.com.soeirosantos.poe.cipher.service.CipherService;
import br.com.soeirosantos.poe.core.exception.CipherException;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AesCipherServiceTest {

    private static final String USER_KEY = "1234567890123456";
    private static final String USER_CONTENT = "some content to be encrypted cão, observação...";
    private static final String ENCRYPTED_CONTENT =
        "phKBm94chNbcCWjsUOjgba1adGvtq1b7m4v2K/Qms5pzKT7NPVflSZPboSpozzz+xKTJqV4UxXP3QMVHcmGWpA==";

    @Autowired
    private CipherService cipherService;

    @Test
    public void encrypt() throws Exception {
        String encrypt = cipherService
            .encrypt(USER_CONTENT, USER_KEY.getBytes(StandardCharsets.UTF_8));
        Assert.assertEquals(ENCRYPTED_CONTENT, encrypt);
    }

    @Test
    public void decrypt() throws Exception {
        String decrypt = cipherService
            .decrypt(ENCRYPTED_CONTENT, USER_KEY.getBytes(StandardCharsets.UTF_8));
        Assert.assertEquals(USER_CONTENT, decrypt);
    }

    @Test(expected = CipherException.class)
    public void secretKeyShouldNotWorkWithInvalidAesKey() {
        cipherService.encrypt(USER_CONTENT, "an invalid key".getBytes(StandardCharsets.UTF_8));
    }

}