package br.com.soeirosantos.poe.tags.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Entity
public class Tag {

    @Id
    private String name;

}
