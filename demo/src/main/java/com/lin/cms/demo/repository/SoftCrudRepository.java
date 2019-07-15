package com.lin.cms.demo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * TODO 分页支持
 */

@NoRepositoryBean
public interface SoftCrudRepository<T, ID> extends CrudRepository<T, ID> {

    @Transactional
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = ?1 and e.deleteTime IS NULL")
    Optional<T> softFindById(ID id);

    @Override
    @Transactional
    default boolean existsById(ID id) {
        return softFindById(id).isPresent();
    }

    @Transactional
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleteTime IS NULL")
    Iterable<T> softFindAll();

    @Transactional
    @Query("SELECT e FROM #{#entityName} e WHERE e.id IN ?1 AND e.deleteTime IS NULL")
    Iterable<T> softFindAllById(Iterable<ID> ids);

    @Transactional
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.deleteTime IS NULL")
    long softCount();

    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleteTime = current_timestamp WHERE e.id = ?1 and e.deleteTime IS NULL")
    void softDeleteById(ID id);
}
