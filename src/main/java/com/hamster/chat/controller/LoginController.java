package com.hamster.chat.controller;

import com.hamster.chat.annotation.SysLogAN;
import com.hamster.chat.base.ResponseResult;
import com.hamster.chat.base.ResultUtil;
import com.hamster.chat.service.SysUserService;
import com.hamster.chat.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "登录 ")
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

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
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return ResponseVo.error("验证码不正确");
//        }
        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return ResultUtil.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ResultUtil.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return ResultUtil.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return ResultUtil.error("账户验证失败");
        }

        return ResultUtil.success();
    }

}
