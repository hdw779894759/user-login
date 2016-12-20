package com.oneapm.user.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserInfo implements Serializable {
    private static final long serialVersionUID = 7046581186920883034L;

    private String username;

    private String password;
}
