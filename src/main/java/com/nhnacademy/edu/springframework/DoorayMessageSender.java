package com.nhnacademy.edu.springframework;

import com.nhn.dooray.client.DoorayHook;
import com.nhn.dooray.client.DoorayHookSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DoorayMessageSender implements MessageSender {
    private final DoorayHookSender doorayHookSender;

    private String hookUrl;

    public DoorayMessageSender(DoorayHookSender doorayHookSender, @Value("${hookurl}") String hookUrl) {
        this.doorayHookSender = doorayHookSender;
        this.hookUrl = hookUrl;
    }

    @MonitorExecutionTime
    @Override
    public boolean sendMessage(User user, String message) {
        try {
            doorayHookSender.send(DoorayHook.builder()
                    .botName(user.getFirstName() + user.getLastName())
                    .text(message)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
