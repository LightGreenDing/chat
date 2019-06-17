package com.hamster.chat.model;

import com.hamster.chat.base.BaseEntity;
import java.util.Date;

public class SysLog extends BaseEntity {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * ID
     * @return id ID
     */
    public Long getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户名
     * @return username 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 用户操作
     * @return operation 用户操作
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 用户操作
     * @param operation 用户操作
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * 请求方法
     * @return method 请求方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 请求方法
     * @param method 请求方法
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 请求参数
     * @return params 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 请求参数
     * @param params 请求参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 执行时长(毫秒)
     * @return time 执行时长(毫秒)
     */
    public Long getTime() {
        return time;
    }

    /**
     * 执行时长(毫秒)
     * @param time 执行时长(毫秒)
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * IP地址
     * @return ip IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * IP地址
     * @param ip IP地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 创建时间
     * @return createtime 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}