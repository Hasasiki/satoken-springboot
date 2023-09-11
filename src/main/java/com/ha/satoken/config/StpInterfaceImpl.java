package com.ha.satoken.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.ha.satoken.service.UserProService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    private final UserProService service;

    public StpInterfaceImpl(UserProService service) {
        this.service = service;
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        if (StpUtil.isLogin()){
            return service.getUserProList(StpUtil.getLoginIdAsLong());
        }
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
