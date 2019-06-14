package com.hamster.chat.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MessageExceptionListener extends ExceptionListenerAdapter {
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
//        String clientId = SocketUtil.getClientId(client);
//        log.error("clientId:[{}]用户触发事件发生异常。{}{}", clientId, e.getMessage(), e);
        client.disconnect();
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
//        String clientId = SocketUtil.getClientId(client);
//        log.error("clientId:[{}]用户断开连接发生异常。{}{}", clientId, e.getMessage(), e);
        client.disconnect();
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {

//        String clientId = SocketUtil.getClientId(client);
//        log.error("clientId:[{}]用户连接发生异常。{}{}", clientId, e.getMessage(), e);
        client.disconnect();
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error("信道异常。{}{}", e.getMessage(), e);
        ctx.close();
        return true;
    }
}
