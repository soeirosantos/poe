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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Bookmark extends AbstractEntity<Long> {

    @NonNull
    private String name;

    @NonNull
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
