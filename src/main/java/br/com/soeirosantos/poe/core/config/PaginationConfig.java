package br.com.soeirosantos.poe.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;

@Configuration
class PaginationConfig extends SpringDataWebConfiguration {

    private static final int MAX_PAGE_SIZE = 200;

    @Bean
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver =
            new PageableHandlerMethodArgumentResolver(sortResolver());

        pageableHandlerMethodArgumentResolver.setMaxPageSize(MAX_PAGE_SIZE);

        return pageableHandlerMethodArgumentResolver;
    }

}
