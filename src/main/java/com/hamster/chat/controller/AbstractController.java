package com.hamster.chat.controller;

import com.hamster.chat.model.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

}

