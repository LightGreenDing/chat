package com.hamster.chat.enums;

/**
 * 返回数据
 */
public enum Enum_Response_Code {
    成功(200, "成功"),
    登陆成功(202, "登陆成功"),

    系统错误(-1, "系统错误"),
    失败(-1000, "失败"),
    用户不存在(-1002, "登录失败,用户名不存在"),
    用户名已存在(-1003, "注册失败,用户名已存在"),
    密码错误(-1004, "登录失败,密码错误"),
    ;
    public int code;
    public String desc;

    private Enum_Response_Code(int _Code, String _Desc) {
        this.code = _Code;
        this.desc = _Desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
