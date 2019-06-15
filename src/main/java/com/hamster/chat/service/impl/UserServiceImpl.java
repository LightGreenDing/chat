package com.hamster.chat.service.impl;

import com.hamster.chat.dao.UserMapper;
import com.hamster.chat.model.User;
import com.hamster.chat.model.UserExample;
import com.hamster.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByAccount(String account) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        return userMapper.selectByExample(userExample).get(0);
    }
}