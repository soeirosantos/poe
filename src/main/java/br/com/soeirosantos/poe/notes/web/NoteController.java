package br.com.soeirosantos.poe.notes.web;

import br.com.soeirosantos.poe.core.web.AbstractController;
import br.com.soeirosantos.poe.notes.domain.entity.Note;
import br.com.soeirosantos.poe.notes.domain.repository.NoteRepository;
import br.com.soeirosantos.poe.security.service.UserContextService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
class NoteController extends AbstractController<Note, Long> {

    NoteController(NoteRepository repository, ApplicationEventPublisher publisher,
        UserContextService userContextService) {
        super(repository, publisher, userContextService);
    }

}
