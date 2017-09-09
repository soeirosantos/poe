package br.com.soeirosantos.poe.tags.web;

import br.com.soeirosantos.poe.notes.domain.repository.NoteRepository;
import br.com.soeirosantos.poe.security.service.UserContextService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags/{name}")
class TagController {

    private final NoteRepository noteRepository;
    private final UserContextService userContextService;

    TagController(NoteRepository noteRepository,
        UserContextService userContextService) {
        this.noteRepository = noteRepository;
        this.userContextService = userContextService;
    }

    @GetMapping("/notes")
    ResponseEntity<?> getNotes(@PathVariable String name) {
        return ResponseEntity
            .ok(noteRepository.findAllByTagsName(name, userContextService.getUsername()));
    }

}
