package com.hamster.chat.controller;

import com.github.pagehelper.PageInfo;
import com.hamster.chat.annotation.SysLogAN;
import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.base.ResultUtil;
import com.hamster.chat.model.SysUser;
import com.hamster.chat.service.SysUserService;
import com.hamster.chat.shiro.ShiroUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.hamster.chat.shiro.ShiroUtils.getUserId;

/**
 * 系统用户控制器
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public ResponseResult list(@RequestParam Map<String, Object> params) {
        PageInfo allPage = sysUserService.getAllPage(params);
        return ResultUtil.success(allPage);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public ResponseResult info() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.success(user);
    }

    /**
     * 修改登录用户密码
     */
    @SysLogAN("修改密码")
    @RequestMapping("/password")
    public ResponseResult password(String password, String newPassword) {

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
//        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
//        if (!flag) {
//            return ResultUtil.error("原密码不正确");
//        }

        return ResultUtil.success();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public ResponseResult info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.getById(userId);

        //获取用户所属的角色列表
//        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
//        user.setRoleIdList(roleIdList);

        return ResultUtil.success(user);
    }

    /**
     * 保存用户
     */
    @SysLogAN("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public ResponseResult save(@RequestBody SysUser user) {
        sysUserService.saveUser(user);
        return ResultUtil.success();
    }

    /**
     * 修改用户
     */
    @SysLogAN("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public ResponseResult update(@RequestBody SysUser user) {

        sysUserService.update(user);

        return ResultUtil.success();
    }

    /**
     * 删除用户
     */
    @SysLogAN("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ResponseResult delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return ResultUtil.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return ResultUtil.error("当前用户不能删除");
        }
        sysUserService.removeByIds(Arrays.asList(userIds));
        return ResultUtil.success();
    }
}
