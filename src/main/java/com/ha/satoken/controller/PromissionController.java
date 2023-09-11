package com.ha.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ha.satoken.data.entity.UserProEntity;
import com.ha.satoken.data.model.ProModel;
import com.ha.satoken.data.repository.UserProRepository;
import com.ha.satoken.eunm.ProType;
import com.ha.satoken.service.UserProService;
import com.ha.satoken.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/pro")
public class PromissionController {
    private final UserService service;
    private final UserProService userProService;

    public PromissionController(UserService service, UserProRepository proRepository, UserProService userProService) {
        this.service = service;
        this.userProService = userProService;
    }

    @GetMapping
    public ProModel getUserPro(){
        ProModel model = new ProModel();
        long loginIdAsLong =  StpUtil.getLoginIdAsLong();
        model.setId(loginIdAsLong);
        model.setName(service.getUser(loginIdAsLong).getUsername());
        model.setProList(userProService.getUserProList(loginIdAsLong));
        return model;
    }

    @GetMapping("{name}")
    public ProModel getUserPro(String name){
        ProModel model = new ProModel();
        long loginIdAsLong =  service.getUser(name).getId();
        model.setId(loginIdAsLong);
        model.setName(name);
        model.setProList(userProService.getUserProList(loginIdAsLong));
        return model;
    }

    @GetMapping("all")
    public List<ProModel> getAllUserPro(){
        return userProService.getAllUserPro();
    }

    @PostMapping("update")
    public void updatePro(Long id, Integer proId){
        String pro = ProType.getEnum(proId).getName();
        userProService.update(id, pro);

    }

    @PostMapping("add")
    public void addPro(Long id, Integer proId){
        String pro = ProType.getEnum(proId).getName();
        userProService.save(id,pro);

    }


    @GetMapping("getProType")
    public List<String> getProType(){
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("user");
        return list;
    }

}
