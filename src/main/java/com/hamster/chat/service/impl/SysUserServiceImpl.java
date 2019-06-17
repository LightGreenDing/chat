package com.hamster.chat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.base.ResultUtil;
import com.hamster.chat.dao.SysUserMapper;
import com.hamster.chat.model.SysUser;
import com.hamster.chat.model.SysUserExample;
import com.hamster.chat.service.SysUserService;
import com.hamster.chat.shiro.ShiroUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class SysUserServiceImpl implements SysUserService {

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        userMapper.insertSelective(user);
    }

    @Override
    public void removeByIds(List<Long> asList) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserIdIn(asList);
        userMapper.deleteByExample(sysUserExample);
    }

    @Override
    public void update(SysUser user) {
        SysUserExample sysUserExample = new SysUserExample();
        userMapper.updateByExample(user, sysUserExample);

    }

    @Override
    public SysUser getById(Long userId) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andUserIdEqualTo(userId);
        List<SysUser> sysUsers = userMapper.selectByExample(sysUserExample);
        return sysUsers.get(0);
    }

    @Override
    public PageInfo getAllPage(Map<String, Object> params) {
        PageHelper.startPage(1,100,"createtime");
        List<SysUser> list = userMapper.selectByExample(null);
        return new PageInfo<SysUser>(list);
    }

}
