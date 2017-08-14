package br.com.soeirosantos.poe;

import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication
public class PoeApplication {

    public static void main(String[] args) {

        SpringApplication.run(PoeApplication.class, args);
    }
}
