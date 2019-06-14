package com.hamster.chat.service;

import com.hamster.chat.dto.MsgInfo;

import java.util.List;

/**
 * 保存聊天记录接口
 */
public interface ChatRecordService {

    /**
     * 连接获取历史聊天记录
     *
     * @param roomId 房间ID
     * @return
     */
    List<MsgInfo> getChatRecordList(String roomId);

    /**
     * 保存消息
     *
     * @param msgInfo 消息
     */
    void save(MsgInfo msgInfo);
}
