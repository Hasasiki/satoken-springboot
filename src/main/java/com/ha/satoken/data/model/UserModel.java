package com.ha.satoken.data.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserModel {

    private Long id;
    private String name;
    private String username;
    private String password;
}
