package br.com.soeirosantos.poe.core.config;

import br.com.soeirosantos.poe.tags.domain.entity.Tag;
import br.com.soeirosantos.poe.tags.domain.entity.Tagable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class JacksonConfig {

    public @Bean
    Module customModule() {
        return new CustomModule();
    }

    static class CustomModule extends SimpleModule {

        CustomModule() {
            setMixInAnnotation(Page.class, CustomModule.PageMixIn.class);
            setMixInAnnotation(Tagable.class, TagableMixIn.class);
        }

        static abstract class PageMixIn {

            @JsonIgnore
            abstract int getNumberOfElements();
        }

        static abstract class TagableMixIn {

            @JsonIgnore
            abstract Set<Tag> getTags();
        }
    }

}
