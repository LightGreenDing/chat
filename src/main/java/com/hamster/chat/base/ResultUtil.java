package com.hamster.chat.base;

public class ResultUtil {

    public static ResponeResult success(Object object) {
        ResponeResult result = new ResponeResult();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ResponeResult success() {
        return success(null);
    }

    public static ResponeResult error(Integer code, String msg) {
        ResponeResult result = new ResponeResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

