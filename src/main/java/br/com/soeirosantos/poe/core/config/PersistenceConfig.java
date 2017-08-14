package br.com.soeirosantos.poe.core.config;

import br.com.soeirosantos.poe.PoeApplication;
import br.com.soeirosantos.poe.security.service.UserContextService;
import br.com.soeirosantos.poe.core.services.impl.AuditorAwareImpl;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({
                        "br.com.soeirosantos.poe.notes.domain.repository",
                        "br.com.soeirosantos.poe.bookmarks.domain.repository",
                        "br.com.soeirosantos.poe.security.domain.repository"
})
@EntityScan(basePackageClasses = {PoeApplication.class, Jsr310JpaConverters.class})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider(UserContextService context) {
        return new AuditorAwareImpl(context);
    }

    @Bean
    Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
