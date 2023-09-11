package com.ha.satoken.service;

import com.ha.satoken.data.entity.UserEntity;
import com.ha.satoken.data.model.UserModel;
import com.ha.satoken.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity getUser(String username) {
        return repository.findByUsername(username);
    }

    public UserEntity getUser(Long id) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        try {
            entity.setName(Objects.requireNonNull(repository.findById(id).orElse(null)).getName());
        } catch (Exception e) {
            entity.setName("does not exist this user");
        }
        return entity;
    }

    public void addUser(UserModel user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        repository.save(entity);
        //添加权限
    }

    public void updateUser(UserModel user) {
        UserEntity entity = repository.findById(user.getId()).orElse(null);
        assert entity != null;
        entity.setName(user.getName());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        repository.save(entity);
    }

    public void deleteUser(UserModel user) {
        repository.deleteById(user.getId());
        //删除用户权限
    }

    public List<UserEntity> getAllUser() {
        return repository.findAll();
    }
}
