package com.oneapm.user.service.impl;

import com.oneapm.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, String> userMap = new HashMap<String, String>() {{
        put("yufan", "123456");
    }};

    @Override
    public boolean isValidUser(String username, String password) {
        String pwd = userMap.get(username);
        return password != null && password.equals(pwd);
    }
}
