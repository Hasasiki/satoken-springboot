package com.ha.satoken.data.model;

import lombok.Data;

import java.util.List;

@Data
public class ProModel {
    public Long id;
    public String name;
    public List<String> proList;
}
