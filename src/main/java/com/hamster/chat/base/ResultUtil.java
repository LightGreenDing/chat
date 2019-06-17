package com.hamster.chat.base;

import com.hamster.chat.enums.Enum_Response_Code;

public class ResultUtil {

    public static ResponseResult success(Object object) {
        ResponseResult result = new ResponseResult();
        result.setCode(Enum_Response_Code.成功.code);
        result.setMsg(Enum_Response_Code.成功.desc);
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

    public static ResponseResult error(String msg) {
        ResponseResult result = new ResponseResult();
        result.setCode(Enum_Response_Code.失败.code);
        result.setMsg(msg);
        return result;
    }
}

