package com.hamster.chat.service;

import com.github.pagehelper.PageInfo;
import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    /**
     * 通过账号获取用户
     *
     * @param account 账号
     * @return 用户
     */
    SysUser findUserByUsername(String account);


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    ResponseResult login(String username, String password);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUser(SysUser user);

    /**
     * 删除用户
     *
     * @param asList
     */
    void removeByIds(List<Long> asList);

    /**
     * 更新用户
     *
     * @param user
     */
    void update(SysUser user);

    SysUser getById(Long userId);


    PageInfo getAllPage(Map<String, Object> params);
}
