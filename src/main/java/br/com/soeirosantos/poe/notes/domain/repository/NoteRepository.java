package br.com.soeirosantos.poe.notes.domain.repository;

import br.com.soeirosantos.poe.core.domain.repository.AbstractRepository;
import br.com.soeirosantos.poe.notes.domain.entity.Note;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends AbstractRepository<Note, Long> {

}
