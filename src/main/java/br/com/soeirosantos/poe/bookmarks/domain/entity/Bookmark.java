package br.com.soeirosantos.poe.bookmarks.domain.entity;

import br.com.soeirosantos.poe.content.domain.entity.Content;
import br.com.soeirosantos.poe.core.domain.entity.AbstractEntity;
import br.com.soeirosantos.poe.tags.domain.entity.Tag;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.Getter;

@Getter
@Entity
public class Bookmark extends AbstractEntity<Long> {

    private String name;

    @Embedded
    private Content content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    @Override
    public void add(Tag tag) {
        tags.add(tag);
    }

    @Override
    public void remove(Tag tag) {
        tags.remove(tag);
    }
}
