package br.com.soeirosantos.poe.notes.domain.entity;

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
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Entity
public class Note extends AbstractEntity<Long> {

    @NotBlank
    @Size(max = 255)
    private String title;

    @Embedded
    @Valid
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
