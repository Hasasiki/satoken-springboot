package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.UserProEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProRepository extends SoftDeleteJPARepository<UserProEntity, Long>{
    @Query(value = "SELECT promission FROM quo_usr_pro WHERE user_id = :id AND active = 1",nativeQuery = true)
    List<String> findByUserId(@Param("id") Long id);
    UserProEntity findAllByUserId(Long id);
}
