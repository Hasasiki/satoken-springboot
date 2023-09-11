package com.ha.satoken.test;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class testController {
    @SaCheckPermission("admin")
    @GetMapping
    public String test(){
        return "that's ok";
    }
}
