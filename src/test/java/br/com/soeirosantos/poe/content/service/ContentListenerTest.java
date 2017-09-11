package br.com.soeirosantos.poe.content.service;


import static org.assertj.core.api.Assertions.assertThat;

import br.com.soeirosantos.poe.content.domain.entity.Content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentListenerTest {

    private static final String BODY = "my content";
    private static final String ENCRYPTED_BODY = "lY8yRDKyq1UZGfXYaBa/7w==";
    private static final String FAKE_TOKEN = "someFakeToken";

    @Autowired
    private ContentListener contentListener;

    @Test
    public void handleEncryptContent() throws Exception {
        Content content = new Content();
        content.setBody(BODY);
        content.setEncrypted(Boolean.TRUE);
        EncryptContentEvent event = new EncryptContentEvent(content, FAKE_TOKEN);
        contentListener.handleEncryptContent(event);
        assertThat(content.getBody()).isEqualTo(ENCRYPTED_BODY);
    }

    @Test
    public void handleDecryptContent() throws Exception {
        Content content = new Content();
        content.setBody(ENCRYPTED_BODY);
        content.setEncrypted(Boolean.TRUE);
        DecryptContentEvent event = new DecryptContentEvent(content, FAKE_TOKEN);
        contentListener.handleDecryptContent(event);
        assertThat(content.getBody()).isEqualTo(BODY);
    }

}