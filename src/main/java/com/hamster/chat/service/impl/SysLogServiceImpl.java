package com.hamster.chat.service.impl;

import com.hamster.chat.dao.SysLogMapper;
import com.hamster.chat.model.SysLog;
import com.hamster.chat.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
        sysLogMapper.insert(sysLog);
    }
}
