package br.com.soeirosantos.poe.content.service;

import br.com.soeirosantos.poe.content.domain.entity.Content;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EncryptContentEvent {

    private final Content content;
    private final String token;

}
