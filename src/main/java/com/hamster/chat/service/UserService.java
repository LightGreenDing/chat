package com.hamster.chat.service;

import com.hamster.chat.model.User;

public interface UserService {
    /**
     * 通过账号获取用户
     *
     * @param account 账号
     * @return 用户
     */
    User findUserByAccount(String account);
}
