package com.ha.satoken.data.repository;

import com.ha.satoken.data.entity.UserEntity;

public interface UserRepository extends SoftDeleteJPARepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
