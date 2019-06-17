package com.hamster.chat.service;

import com.hamster.chat.model.SysLog;

public interface SysLogService {
    /**
     * 增加日志记录
     *
     * @param sysLog 日志记录
     */
    void save(SysLog sysLog);
}
