package br.com.soeirosantos.poe.tags.domain.entity;

import java.util.Set;

public interface Tagable {

    void add(Tag tag);

    void remove(Tag tag);

    Set<Tag> getTags();
}
