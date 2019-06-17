package com.hamster.chat.service;

import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.model.User;

public interface UserService {
    /**
     * 通过账号获取用户
     *
     * @param account 账号
     * @return 用户
     */
    User findUserByUsername(String account);


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    ResponseResult login(String username, String password);
}
