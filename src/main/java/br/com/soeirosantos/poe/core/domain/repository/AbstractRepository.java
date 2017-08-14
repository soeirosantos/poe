package br.com.soeirosantos.poe.core.domain.repository;

import br.com.soeirosantos.poe.core.domain.entity.AbstractEntity;
import br.com.soeirosantos.poe.notes.domain.entity.Note;
import java.io.Serializable;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity<ID>, ID extends Serializable> extends
    JpaRepository<T, ID> {

    @Query("select e from #{#entityName} e where e.id in ?1 and e.createdBy = ?2")
    T findOne(ID id, String username);

    @Query("select e from #{#entityName} e where e.createdBy = ?1")
    Page<T> findAll(String username, Pageable pageable);

    @Query("select e from #{#entityName} e join e.tags t where t.name = ?1 and e.createdBy = ?2")
    Set<Note> findAllByTagsName(String name, String username);
}
