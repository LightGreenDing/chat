package com.hamster.chat.controller;

import com.hamster.chat.annotation.SysLogAN;
import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return token
     */
    @SysLogAN("登录")
    @GetMapping("/login")
    public ResponseResult login(String username, String password) {
        return userService.login(username, password);
    }

}
