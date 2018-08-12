package com.xangqun.springcloud.consumerfeign.websocket;

import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

@Service
public class SendServiceImpl implements SendService {

    @Override
    public void sendBatch(List<Session> sessionList, String message) throws IOException {
        for (Session session : sessionList) {
            sendMessage(session, message);
        }
    }

    @Override
    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
