package com.hamster.chat.model;

import com.hamster.chat.base.BaseEntity;
import java.util.Date;

public class User extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 状态(1启用0禁用)
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date cretetime;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 盐
     */
    private String salt;

    /**
     * ID
     * @return id ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 账号
     * @return username 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 账号
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 昵称
     * @return nickname 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 状态(1启用0禁用)
     * @return state 状态(1启用0禁用)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态(1启用0禁用)
     * @param state 状态(1启用0禁用)
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 创建时间
     * @return cretetime 创建时间
     */
    public Date getCretetime() {
        return cretetime;
    }

    /**
     * 创建时间
     * @param cretetime 创建时间
     */
    public void setCretetime(Date cretetime) {
        this.cretetime = cretetime;
    }

    /**
     * 密码
     * @return pwd 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 密码
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * 盐
     * @return salt 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 盐
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}