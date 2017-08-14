package br.com.soeirosantos.poe.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Security security = new Security();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final CryptContent cryptContent = new CryptContent();

    @Getter
    @Setter
    public static class Security {

        private Integer tokenExpirationTime;
        private Integer refreshTokenExpTime;
        private String tokenIssuer;
        private String tokenSigningKey;

    }

    @Getter
    @Setter
    public static class CryptContent {

        private String salt;
        private String aesKeyIv;

    }

}
