package com.hamster.chat.controller;

import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.base.ResultUtil;
import com.hamster.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseResult login() {
        return ResultUtil.success(userService.findUserByAccount("admin"));
    }
}
