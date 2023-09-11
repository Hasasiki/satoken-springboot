package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.ActivatableEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends ActivatableEntity<ID>, ID extends Serializable> extends CrudRepository<T, ID>, QueryByExampleExecutor<T> {

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.active = true")
    List<T> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id in ?1 and e.active = true")
    List<T> findAllById(Iterable<ID> ids);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and e.active = true")
    Optional<T> findById(ID id);

    //Look up deleted entities
    @Query("select e from #{#entityName} e where e.active = false")
    @Transactional(readOnly = true)
    List<T> findInactive();

    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where e.active = true")
    long count();

    @Override
    @Transactional(readOnly = true)
    @Query("select 1 from #{#entityName} e where e.id = ?1 and e.active = true")
    boolean existsById(ID id);

    @Override
    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.active = false where e.id = ?1")
    void deleteById(ID id);

    @Override
    @Transactional
    default void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(en -> deleteById(en.getId()));
    }

    @Override
    @Query("update #{#entityName} e set e.active = false")
    @Transactional
    @Modifying
    void deleteAll();

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.active = true where e.id = ?1")
    void activeById(ID id);

    @Transactional
    default void activeAllById(Iterable<? extends T> entities) {
        entities.forEach(en -> activeById(en.getId()));
    }
}
