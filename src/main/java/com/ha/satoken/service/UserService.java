package com.ha.satoken.service;

import com.ha.satoken.data.entity.UserEntity;
import com.ha.satoken.data.entity.UserProEntity;
import com.ha.satoken.data.model.UserModel;
import com.ha.satoken.data.repository.UserProRepository;
import com.ha.satoken.data.repository.UserRepository;
import com.ha.satoken.data.eunm.ProType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;
    private final UserProRepository proRepository;

    public UserService(UserRepository repository, UserProRepository proRepository) {
        this.repository = repository;
        this.proRepository = proRepository;
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
        UserProEntity e = new UserProEntity();
        e.setUserId(getUser(user.getUsername()).getId());
        e.setPromission(ProType.USER.getName());
        proRepository.save(e);
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
        proRepository.findAllByUserId(user.getId()).forEach(e -> proRepository.deleteById(e.getId()));
    }

    public List<UserEntity> getAllUser() {
        return repository.findAll();
    }

    public UserEntity getUserByOpenId(String openId) {
        //todo uncomment this when user entity has openId field
        //判断是否存在该用户
        if (repository.findByOpenId(openId).isPresent()) {
            return repository.findByOpenId(openId).orElse(null);
        }
        return null;
    }
}
