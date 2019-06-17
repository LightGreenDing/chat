package com.hamster.chat.shiro;

import com.hamster.chat.exception.CustomizeException;
import com.hamster.chat.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {
    // 加密算法
    public final static String hashAlgorithmName = "SHA-256";
    // 循环次数
    public final static int hashIterations = 16;

    // sha256加密算法
    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    // 获取session
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    // 获取对象
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    // 获取用户对象
    public static SysUser getUserEntity() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    // 获取用户ID
    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    // 赋值
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    // 获取
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    // 判断是否登录
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    // 登出
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    // 获取图片验证码
    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new CustomizeException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}
