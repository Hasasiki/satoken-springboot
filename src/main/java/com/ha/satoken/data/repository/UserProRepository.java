package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.UserProEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface UserProRepository extends SoftDeleteJPARepository<UserProEntity, Long>{
    List<UserProEntity> findAllByUserId(Long id);


    void deleteByUserId(Long id);

    void deleteAllByUserId(Long id);

}
