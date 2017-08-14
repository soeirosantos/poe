package br.com.soeirosantos.poe.content.domain.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Embeddable
public class Content {

    @Setter
    @Size(max = 1000)
    private String body;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private final Boolean encrypted = Boolean.FALSE;

}
