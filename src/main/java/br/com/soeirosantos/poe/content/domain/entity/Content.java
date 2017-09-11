package br.com.soeirosantos.poe.content.domain.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Embeddable
public class Content {

    @Size(max = 1000)
    private String body;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean encrypted = Boolean.FALSE;

}
