package br.com.soeirosantos.poe.security.auth.jwt.extractor;

public interface TokenExtractor {

    String HEADER_PREFIX = "Bearer ";

    String extract(String payload);

}
