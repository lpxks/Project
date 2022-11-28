package com.root.pojo;

import lombok.Data;

@Data
public class loginUser {

    private String username;

    private String password;

    private String verifiCode;

    private Integer userType;
}
