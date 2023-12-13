package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends SoftDeleteJPARepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query("select e from #{#entityName} e where e.openId = ?1 and e.active = true")
    Optional<UserEntity> findByOpenId(String openId);
}
