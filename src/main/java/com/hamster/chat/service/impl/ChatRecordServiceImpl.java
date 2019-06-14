package com.hamster.chat.service.impl;

import com.hamster.chat.dto.MsgInfo;
import com.hamster.chat.service.ChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 保存聊天记录实现类
 */
@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MsgInfo> getChatRecordList(String roomId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        Query query = new Query();
        query.limit(30);
        query.addCriteria(Criteria.where("roomId").is(roomId));
        query.with(sort);
        List<MsgInfo> msgInfos = mongoTemplate.find(query, MsgInfo.class);
//        查询出来的30条进行倒叙展示
        Collections.reverse(msgInfos);
        return msgInfos;
    }

    @Override
    public void save(MsgInfo msgInfo) {
        mongoTemplate.insert(msgInfo);
    }
}
