package com.ha.satoken.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.ha.satoken.data.entity.UserEntity;
import com.ha.satoken.data.model.UserModel;
import com.ha.satoken.data.repository.UserProRepository;
import com.ha.satoken.data.repository.UserRoleRepository;
import com.ha.satoken.service.UserService;
import com.ha.satoken.utils.WeChatUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService service;
    private final UserProRepository proRepository;

    public UserController(UserService service, UserProRepository proRepository) {
        this.service = service;
        this.proRepository = proRepository;
    }

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        UserEntity user = service.getUser(username);

        if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
            // `SaLoginModel`为登录参数Model，其有诸多参数决定登录时的各种逻辑，例如：
            StpUtil.login(user.getId(), new SaLoginModel()
                    .setDevice("PC")                // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                    .setIsLastingCookie(true)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                    .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
            );
            List<String> list = new ArrayList<>();
            proRepository.findAllByUserId(user.getId()).stream().map(e -> {
                list.add(e.getPromission());
                return e;
            }).collect(Collectors.toList());
            String role = "user";
            for (String s:list) {
                if (s.equals("admin")){
                    role = s;
                }
            }
            return role;
        }
        return "登录失败";
    }

    @RequestMapping("loginCheck")
    public SaResult loginCheck(String code) {
        //拿到code去微信服务器获取openid
        String openid = WeChatUtil.getOpenId(code);

        if (openid.equals("invalid")) {
            return SaResult.error("code无效");
        } else {
            UserEntity user = service.getUserByOpenId(openid);
            if (user != null) {
                // `SaLoginModel`为登录参数Model，其有诸多参数决定登录时的各种逻辑，例如：
                StpUtil.login(user.getId(), new SaLoginModel()
                        .setDevice("WX")                // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                        .setIsLastingCookie(true)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                        .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
                );
                SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                return SaResult.data(tokenInfo);
            } else {
                return SaResult.error("无用户信息");
            }
        }
    }

    //you need delete this when you deploy
    @RequestMapping("loginTest")
    public SaResult fakeLogin(String username) {
        UserEntity user = service.getUser(username);
        if (user != null) {
            // `SaLoginModel`为登录参数Model，其有诸多参数决定登录时的各种逻辑，例如：
            StpUtil.login(user.getId(), new SaLoginModel()
                    .setDevice("WX")                // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                    .setIsLastingCookie(true)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                    .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
            );
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SaResult.data(tokenInfo);
        }
        return SaResult.error("登录失败");
    }

    // if you need use this, you need to add a new column named openid in table user
    @RequestMapping("wxfLogin")
    public SaResult firstLoginByWx(String username, String password, String code) {
//        UserEntity user = service.getUser(username);
//        String openid = WeChatUtil.getOpenId(code);
//        //todo 测试替换,实测删除
////        String openid = "123456";
//
//        if (openid.equals("invalid")) {
//            return SaResult.error("code无效");
//        }
//
//        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
//            //判断openid是否为空,如果为空则为首次登录,更新openid,如果不为空则比较openid是否相同,如果相同则登录成功,如果不同则登录失败
//            if (user.getOpenId() == null || Objects.equals(user.getOpenId(), "")) {
//                //向该用户添加openid
//                UserModel userModel = new UserModel();
//                userModel.setId(user.getId());
//                userModel.setName(user.getName());
//                userModel.setUsername(user.getUsername());
//                userModel.setPassword(user.getPassword());
//                userModel.setOpenId(openid);
//                //首次登录将openid记入数据库
//                service.updateUser(userModel);
//                service.login(user);
//                String token = StpUtil.getTokenValue();
//                return SaResult.data(token);
//            } else if (user.getOpenId().equals(openid)) {
//                service.login(user);
//                String token = StpUtil.getTokenValue();
//                return SaResult.data(token);
//            }
//        } else if (user.getUsername().equals(username) && user.getPassword().equals(password) && !user.getOpenId().equals(openid)) {
//            return SaResult.error("该用户已绑定其他微信号");
//        } else if (user.getUsername().equals(username) && !user.getPassword().equals(password)) {
//            return SaResult.error("密码错误");
//        } else if (!user.getUsername().equals(username) && user.getPassword().equals(password)) {
//            return SaResult.error("用户名错误");
//        }
        return SaResult.error("登录失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @SaCheckPermission("admin")
    @PostMapping("addUser")
    public void addUser(@RequestBody UserModel user) {
        service.addUser(user);
    }

    @SaCheckPermission("admin")
    @PostMapping("updateUser")
    public void updateUser(@RequestBody UserModel user) {
        service.updateUser(user);
    }

    @SaCheckPermission("admin")
    @DeleteMapping
    public void deleteUser(@RequestBody UserModel user) {
        service.deleteUser(user);
    }

    @GetMapping
    public UserModel getUser(@RequestParam String username) {
        UserEntity user = service.getUser(username);
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setName(user.getName());
        model.setUsername(user.getUsername());
        model.setPassword(user.getPassword());
        return model;
    }

    @SaCheckPermission ("admin")
    @GetMapping("all")
    public List<UserModel> getAllUser() {
        List<UserEntity> users = service.getAllUser();
        List<UserModel> models = new ArrayList<>();
        for (UserEntity user:users) {
            UserModel model = new UserModel();
            model.setId(user.getId());
            model.setName(user.getName());
            model.setUsername(user.getUsername());
            model.setPassword(user.getPassword());
            models.add(model);
        }
        return models;
    }
}
