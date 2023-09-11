package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.ActivatableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoRepositoryBean
public interface SoftDeleteJPARepository<T extends ActivatableEntity<ID>, ID extends Serializable> extends SoftDeleteRepository<T, ID>, JpaRepository<T, ID> {

    @Override
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    default void deleteAllInBatch(Iterable<T> entities) {
        deleteAllByIdInBatch(StreamSupport.stream(entities.spliterator(), false).map(ActivatableEntity::getId).collect(Collectors.toList()));
    }

    @Override
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update #{#entityName} e set e.active = false where e.id in :ids")
    void deleteAllByIdInBatch(@Param("ids") Iterable<ID> ids);

    @Override
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update #{#entityName} e set e.active = false")
    void deleteAllInBatch();

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("update #{#entityName} e set e.active = true where e.id in :ids")
    void activeAllByIdInBatch(@Param("ids") Iterable<ID> ids);

    @Override
    default T getById(ID id) {
        return findById(id).orElse(null);
    }
}
