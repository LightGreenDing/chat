package com.hamster.chat.handler;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hamster.chat.dto.ClientInfo;
import com.hamster.chat.dto.MsgInfo;
import com.hamster.chat.redis.RedisService;
import com.hamster.chat.service.ChatRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * socket服务启动
 * CommandLineRunner 项目启动运行....
 */
@Slf4j
@Component
public class MessageEventHandler implements CommandLineRunner {
    //静态变量，用来记录当前在线连接数。（原子类、线程安全）
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //会话集合
    private static final ConcurrentSkipListMap<String, ClientInfo> webSocketMap = new ConcurrentSkipListMap<>();
    @Resource
    private SocketIOServer socketIOServer;
    @Resource
    private RedisService redisService;
    @Autowired
    private ChatRecordService chatRecordService;

    /*
     * 客户端链接事件
     * */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String account = client.getHandshakeData().getSingleUrlParam("account");
        UUID sessionId = client.getSessionId();

        System.out.println("登录用户:" + account);
        ClientInfo clientInfo = webSocketMap.get(account);
        if (null == clientInfo) {
            clientInfo = new ClientInfo();
            clientInfo.setOnline(true);
            //在线数加1
            log.info("socket 建立新连接、用户:" + account + "、当前连接数：" + onlineCount.incrementAndGet());
        }
        clientInfo.setLeastSignificantBits(sessionId.getLeastSignificantBits());
        clientInfo.setMostSignificantBits(sessionId.getMostSignificantBits());
        clientInfo.setLastConnectedTime(new Date());
        //将会话信息更新保存至集合中
        webSocketMap.put(account, clientInfo);
        //房间号
        String roomId = client.getHandshakeData().getSingleUrlParam("rm");
        //加入房间
        client.joinRoom(roomId);
        //往聊天室中发送进入房间信息
        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(roomId);
        roomOperations.sendEvent("intoRoom", account + "加入房间");
        //获取历史消息发送给前台
        List<MsgInfo> msgInfos = chatRecordService.getChatRecordList(roomId);
        roomOperations.sendEvent("getHistoryMsgInfo", msgInfos);
    }

    /**
     * 客户端断开连接事件
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String account = client.getHandshakeData().getSingleUrlParam("account");
        String message = account + "断开连接";
        System.out.println(message);
        client.sendEvent("closed", message);
        webSocketMap.remove(account);
        //在线数减1
        log.info("socket 断开连接、account:" + account + "、当前连接数：" + onlineCount.decrementAndGet());

    }

    /**
     * 交谈事件
     *
     * @param client
     */
    @OnEvent(value = "message")
    public void onMessage(SocketIOClient client, MsgInfo msgInfo) {
        System.out.println("交谈事件---" + msgInfo.getAccount() + ":" + msgInfo.getContent());
        String account = client.getHandshakeData().getSingleUrlParam("account");
        //房间号
        String rm = client.getHandshakeData().getSingleUrlParam("rm");
        BroadcastOperations roomOperations = socketIOServer.getRoomOperations(rm);
        msgInfo.setTime(new Date());
        msgInfo.setRoomId(rm);
        //向房间发送消息
        roomOperations.sendEvent("message", msgInfo);
        //保存到数据库
        chatRecordService.save(msgInfo);
    }

    /*
     * socket启用
     * */
    @Override
    public void run(String... args) throws Exception {
//        redisService.makeIpaddress();       //缓存白名单
        socketIOServer.start();
    }
}
