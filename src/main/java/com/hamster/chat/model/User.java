package com.hamster.chat.model;

import com.hamster.chat.base.BaseEntity;

public class User extends BaseEntity {
    /**
     * 编码
     */
    private Integer id;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态(0禁用1启用)
     */
    private Boolean status;

    /**
     * 编码
     * @return id 编码
     */
    public Integer getId() {
        return id;
    }

    /**
     * 编码
     * @param id 编码
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     * @return nickName 用户名
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 用户名
     * @param nickname 用户名
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 账号
     * @return account 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 账号
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 状态(0禁用1启用)
     * @return status 状态(0禁用1启用)
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 状态(0禁用1启用)
     * @param status 状态(0禁用1启用)
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}