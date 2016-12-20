package com.oneapm.user.web;

import com.oneapm.user.entity.OneAPMPubKey;
import com.oneapm.user.request.EncryptedRequest;
import com.oneapm.user.request.LoginUserInfo;
import com.oneapm.user.service.KeyService;
import com.oneapm.user.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.logging.Level;

import static com.alibaba.fastjson.JSON.toJSONString;

@Controller
@Log
public class UserController {

    private UserService userService;

    private KeyService keyService;

    @Autowired
    public UserController(UserService userService, KeyService keyService) {
        this.userService = userService;
        this.keyService = keyService;
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> login(@RequestBody EncryptedRequest request) {
        Optional<LoginUserInfo> userInfo = keyService.decrypt(request, LoginUserInfo.class);
        if (userInfo.isPresent()) {
            LoginUserInfo user = userInfo.get();
            log.log(Level.INFO, "Login User info" + toJSONString(user));
            return ResponseEntity.ok(userService.isValidUser(user.getUsername(), user.getPassword()));
        }
        return ResponseEntity.badRequest().body(false);
    }

    @RequestMapping(value = "getKey", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<OneAPMPubKey> getEncryptKey() {
        OneAPMPubKey key = keyService.getKey();
        log.log(Level.WARNING, toJSONString(key));
        return ResponseEntity.ok(key);
    }
}
