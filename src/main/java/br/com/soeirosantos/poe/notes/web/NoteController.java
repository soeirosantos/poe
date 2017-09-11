package br.com.soeirosantos.poe.notes.web;

import br.com.soeirosantos.poe.content.web.ContentToken;
import br.com.soeirosantos.poe.core.web.GenericController;
import br.com.soeirosantos.poe.notes.domain.entity.Note;
import br.com.soeirosantos.poe.notes.domain.repository.NoteRepository;
import br.com.soeirosantos.poe.security.service.UserContextService;
import br.com.soeirosantos.poe.tags.domain.entity.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NoteController.PATH)
class NoteController {

    protected static final String PATH = "/api/notes";

    private final GenericController<Note, Long> genericController;

    NoteController(NoteRepository repository, ApplicationEventPublisher publisher,
        UserContextService userContextService) {
        genericController = new GenericController<>(repository, publisher, userContextService);
    }

    @PostMapping
    ResponseEntity<?> save(@Valid @RequestBody
        Note entity,
        @RequestHeader(required = false, name = ContentToken.HEADER_NAME) String token) {
        return genericController.save(entity, token);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Long id) {
        return genericController.get(id);
    }

    @GetMapping("/{id}/decrypt")
    ResponseEntity<?> decrypt(@PathVariable Long id,
        @RequestHeader(required = false, name = ContentToken.HEADER_NAME) String token) {
        return genericController.decrypt(id, token);
    }

    @GetMapping
    ResponseEntity<?> list(
        Pageable pageable) {
        return genericController.list(pageable);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id,
        @RequestBody Note entity,
        @RequestHeader(required = false, name = ContentToken.HEADER_NAME) String token) {
        return genericController.update(id, entity, token);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }

    @PostMapping("/{id}/tags")
    ResponseEntity<?> addTag(@PathVariable Long id,
        @RequestBody List<Tag> tags) {
        return genericController.addTag(id, tags);
    }

    @GetMapping("/{id}/tags")
    ResponseEntity<?> getTags(@PathVariable Long id) {
        return genericController.getTags(id);
    }

    @DeleteMapping("/{id}/tags")
    ResponseEntity<?> removeTags(@PathVariable Long id,
        @RequestBody List<Tag> tags) {
        return genericController.removeTags(id, tags);
    }

}
