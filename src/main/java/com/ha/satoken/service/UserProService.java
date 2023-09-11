package com.ha.satoken.service;

import com.ha.satoken.data.entity.UserEntity;
import com.ha.satoken.data.entity.UserProEntity;
import com.ha.satoken.data.model.ProModel;
import com.ha.satoken.data.repository.UserProRepository;
import com.ha.satoken.data.repository.UserRepository;
import com.ha.satoken.eunm.ProType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserProService {
    private final UserProRepository repository;
    private final UserRepository userRepository;


    public UserProService(UserProRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<String> getUserProList(long id) {
        List<String> list = new ArrayList<>();
        repository.findAllByUserId(id).stream().map(e -> {
            list.add(e.getPromission());
            return e;
        }).collect(Collectors.toList());
        return list;
    }

    public List<ProModel> getAllUserPro() {
        List<ProModel> list = new ArrayList<>();
        userRepository.findAll().stream().map(user -> {
            ProModel model = new ProModel();
            model.setId(user.getId());
            model.setName(user.getName());
            model.setProList(getUserProList(user.getId()));
            list.add(model);
            return user;
        }).collect(Collectors.toList());
        return list;
    }

    public void update(Long id, String pro) {
        repository.findById(id).ifPresent(e -> {
            e.setPromission(pro);
            repository.save(e);
        });
    }

    public void save(Long id, String pro) {
        UserProEntity e = new UserProEntity();
        e.setUserId(id);
        e.setPromission(pro);
        repository.save(e);
    }
    public UserEntity getUser(Long id) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        try {
            entity.setName(Objects.requireNonNull(userRepository.findById(id).orElse(null)).getName());
        } catch (Exception e) {
            entity.setName("does not exist this user");
        }
        return entity;
    }
}
