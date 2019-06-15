package com.hamster.chat.base;

public class ResultUtil {

    public static ResponseResult success(Object object) {
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult error(Integer code, String msg) {
        ResponseResult result = new ResponseResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

