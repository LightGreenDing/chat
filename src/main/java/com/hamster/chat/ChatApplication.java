package com.hamster.chat;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.hamster.chat.exception.MessageExceptionListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

@SpringBootApplication
@MapperScan("com.hamster.chat.dao")
public class ChatApplication {
    @Value("${socket.host}")
    private String socketHost;
    @Value("${socket.port}")
    private int socketPort;


    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(socketHost);
        config.setPort(socketPort);
        // 开放跨域
        config.setOrigin(null);
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        config.setExceptionListener(new MessageExceptionListener());
        //设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
        config.setMaxFramePayloadLength(1024 * 1024);
        //设置http交互最大内容长度
        config.setMaxHttpContentLength(1024 * 1024);
        //身份验证
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                String account = data.getSingleUrlParam("account");
                String timeLong = data.getSingleUrlParam("timeLong");
                String rm = data.getSingleUrlParam("rm");
                String pd = data.getSingleUrlParam("pd");
                return true;
            }
        });

        config.getSocketConfig().setReuseAddress(true);
        config.getSocketConfig().setSoLinger(0);
        //服务是低延迟的
        config.getSocketConfig().setTcpNoDelay(true);
        //可以探测客户端的连接是否还存活着
        config.getSocketConfig().setTcpKeepAlive(true);

        return server = new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @PreDestroy
    public void destory() {
        server.stop();
    }


    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
