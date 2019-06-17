package com.hamster.chat.service.impl;

import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.base.ResultUtil;
import com.hamster.chat.dao.SysUserMapper;
import com.hamster.chat.model.SysUser;
import com.hamster.chat.model.SysUserExample;
import com.hamster.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser findUserByUsername(String account) {
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andUsernameEqualTo(account);
        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public ResponseResult login(String username, String password) {
        return ResultUtil.success();
    }

}
