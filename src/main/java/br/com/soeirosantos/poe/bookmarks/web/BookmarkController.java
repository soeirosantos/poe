package br.com.soeirosantos.poe.bookmarks.web;

import br.com.soeirosantos.poe.bookmarks.domain.entity.Bookmark;
import br.com.soeirosantos.poe.bookmarks.domain.repository.BookmarkRepository;
import br.com.soeirosantos.poe.core.domain.repository.AbstractRepository;
import br.com.soeirosantos.poe.core.web.AbstractController;
import br.com.soeirosantos.poe.security.service.UserContextService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
class BookmarkController extends AbstractController<Bookmark, Long> {

    protected BookmarkController(
        BookmarkRepository repository,
        ApplicationEventPublisher publisher,
        UserContextService userContextService) {
        super(repository, publisher, userContextService);
    }
}
