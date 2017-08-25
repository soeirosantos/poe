package br.com.soeirosantos.poe.core.web;

import br.com.soeirosantos.poe.content.service.DecryptContentEvent;
import br.com.soeirosantos.poe.content.service.EncryptContentEvent;
import br.com.soeirosantos.poe.content.web.ContentToken;
import br.com.soeirosantos.poe.core.domain.entity.AbstractEntity;
import br.com.soeirosantos.poe.core.domain.repository.AbstractRepository;
import br.com.soeirosantos.poe.core.exception.EntityNotFound;
import br.com.soeirosantos.poe.security.service.UserContextService;
import br.com.soeirosantos.poe.tags.domain.entity.Tag;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public class GenericController<T extends AbstractEntity<ID>, ID extends Serializable> {

    private final ApplicationEventPublisher publisher;
    private final AbstractRepository<T, ID> repository;
    private final UserContextService userContextService;

    public GenericController(AbstractRepository<T, ID> repository,
        ApplicationEventPublisher publisher,
        UserContextService userContextService) {
        this.repository = repository;
        this.publisher = publisher;
        this.userContextService = userContextService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody T entity,
        @RequestHeader(required = false, name = ContentToken.HEADER_NAME) String token) {
        publisher.publishEvent(new EncryptContentEvent(entity.getContent(), token));
        return new ResponseEntity<T>(repository.save(entity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ID id) {
        throwsErrorIfNotFound(id);
        return ResponseEntity.ok(repository.findOne(id, userContextService.getUsername()));
    }

    @GetMapping("/{id}/decrypt")
    public ResponseEntity<?> decrypt(@PathVariable ID id,
        @RequestHeader(name = ContentToken.HEADER_NAME) String token) {
        throwsErrorIfNotFound(id);
        T entity = repository.findOne(id, userContextService.getUsername());
        publisher.publishEvent(new DecryptContentEvent(entity.getContent(), token));
        return ResponseEntity.ok(entity);
    }

    @GetMapping
    public ResponseEntity<?> list(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(userContextService.getUsername(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity,
        @RequestHeader(required = false, name = ContentToken.HEADER_NAME) String token) {
        throwsErrorIfNotFound(id);
        publisher.publishEvent(new EncryptContentEvent(entity.getContent(), token));
        return ResponseEntity.ok(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        throwsErrorIfNotFound(id);
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<?> addTag(@PathVariable ID id, @RequestBody List<Tag> tags) {
        throwsErrorIfNotFound(id);
        T entity = repository.findOne(id, userContextService.getUsername());
        tags.forEach(t -> entity.add(t));
        repository.save(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<?> getTags(@PathVariable ID id) {
        throwsErrorIfNotFound(id);
        T entity = repository.findOne(id, userContextService.getUsername());
        return ResponseEntity.ok(entity.getTags());
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<?> removeTags(@PathVariable ID id, @RequestBody List<Tag> tags) {
        throwsErrorIfNotFound(id);
        T entity = repository.findOne(id, userContextService.getUsername());
        tags.forEach(t -> entity.remove(t));
        repository.save(entity);
        return ResponseEntity.ok().build();
    }

    private void throwsErrorIfNotFound(ID id) {
        if (repository.findOne(id, userContextService.getUsername()) == null) {
            throw new EntityNotFound();
        }
    }

}
